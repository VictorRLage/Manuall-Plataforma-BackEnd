package manuall.api.service

import manuall.api.domain.Administrador
import manuall.api.domain.Mensagem
import manuall.api.domain.Contratante
import manuall.api.domain.Prestador
import manuall.api.dto.mensagem.*
import manuall.api.repository.MensagemRepository
import manuall.api.repository.SolicitacaoRepository
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService(
    val mensagemRepository: MensagemRepository,
    val solicitacaoRepository: SolicitacaoRepository,
    val jwtTokenManager: JwtTokenManager,
    private val template: SimpMessagingTemplate
) {

    fun buscarTodos(token: String?): ResponseEntity<List<Mensagem>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(mensagemRepository.findAll())
    }

    fun getChats(token: String?): ResponseEntity<List<ChatResponse>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val solicitacoes = when (usuario) {
            is Contratante -> solicitacaoRepository.findByContratanteIdAndStatus2AndDataFimNull(usuario.id)
            is Prestador -> solicitacaoRepository.findByPrestadorIdAndStatus2AndDataFimNull(usuario.id)
            else -> return ResponseEntity.status(401).build()
        }

        val chats = solicitacoes.map { solicitacao ->
            when (usuario) {
                is Contratante -> ChatResponse(
                    solicitacao.id,
                    solicitacao.prestador.nome,
                    solicitacao.prestador.anexoPfp,
                    solicitacao.mensagem.map { mensagem ->
                        MensagemDto(
                            mensagem.id,
                            mensagem.visto,
                            mensagem.idRemetente == usuario.id,
                            mensagem.horario,
                            mensagem.texto,
                            mensagem.anexo,
                        )
                    },
                    solicitacao.dataInicio,
                )
                is Prestador -> ChatResponse(
                    solicitacao.id,
                    solicitacao.contratante.nome,
                    null,
                    solicitacao.mensagem.map { mensagem ->
                        MensagemDto(
                            mensagem.id,
                            mensagem.visto,
                            mensagem.idRemetente == usuario.id,
                            mensagem.horario,
                            mensagem.texto,
                            mensagem.anexo,
                        )
                    },
                    solicitacao.dataInicio,
                )
                else -> return ResponseEntity.status(401).build()
            }
        }

        return ResponseEntity.status(if (chats.isEmpty()) 204 else 200).body(chats)

    }

    fun mandarMensagem(chatMensagemRequest: ChatMensagemRequest) {

        val usuario = jwtTokenManager.validateToken("Bearer ${chatMensagemRequest.token}")
            ?: return

        val possivelSolicitacao = solicitacaoRepository.findById(chatMensagemRequest.solicitacaoId)

        if (possivelSolicitacao.isEmpty)
            return

        val solicitacao = possivelSolicitacao.get()

        val mensagem = Mensagem()
        mensagem.solicitacao = solicitacao
        mensagem.texto = chatMensagemRequest.mensagem
        mensagem.horario = Date()
        mensagem.anexo = chatMensagemRequest.anexo
        mensagem.idRemetente = usuario.id
        mensagem.visto = false

        val id = mensagemRepository.save(mensagem).id

        template.convertAndSend("/mensagem/${solicitacao.contratante.id}",
            MensagemDtoSolo(
                solicitacao.id,
                if (usuario is Contratante) chatMensagemRequest.tempId else null,
                id,
                false,
                usuario is Contratante,
                mensagem.horario,
                mensagem.texto,
                mensagem.anexo
            )
        )
        template.convertAndSend("/mensagem/${solicitacao.prestador.id}",
            MensagemDtoSolo(
                solicitacao.id,
                if (usuario is Prestador) chatMensagemRequest.tempId else null,
                id,
                false,
                usuario is Prestador,
                mensagem.horario,
                mensagem.texto,
                mensagem.anexo
            )
        )
    }

    fun visualizarMensagem(verMensagemRequest: VerMensagemRequest) {

        val usuario = jwtTokenManager.validateToken("Bearer ${verMensagemRequest.token}")
            ?: return

        val possivelMensagem = mensagemRepository.findById(verMensagemRequest.mensagemId)

        if (possivelMensagem.isEmpty)
            return

        val mensagem = possivelMensagem.get()

        mensagem.visto = true

        mensagemRepository.save(mensagem)

        template.convertAndSend("/visualizacao/${mensagem.solicitacao.contratante.id}",
            MensagemVisualizadaDto(
                mensagem.solicitacao.id,
                mensagem.id
            )
        )
        template.convertAndSend("/visualizacao/${mensagem.solicitacao.prestador.id}",
            MensagemVisualizadaDto(
                mensagem.solicitacao.id,
                mensagem.id
            )
        )
    }
}