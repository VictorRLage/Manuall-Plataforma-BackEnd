package manuall.api.service

import manuall.api.domain.Administrador
import manuall.api.domain.Chat
import manuall.api.domain.Contratante
import manuall.api.domain.Prestador
import manuall.api.dto.chat.*
import manuall.api.repository.ChatRepository
import manuall.api.repository.SolicitacaoRepository
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class ChatService(
    val chatRepository: ChatRepository,
    val solicitacaoRepository: SolicitacaoRepository,
    val jwtTokenManager: JwtTokenManager
) {

    fun buscarTodos(token: String?): ResponseEntity<List<Chat>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(chatRepository.findAll())
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
                    solicitacao.chat.map { mensagem ->
                        MensagemDto(
                            mensagem.idRemetente == usuario.id,
                            mensagem.horario,
                            mensagem.mensagem,
                            mensagem.anexo,
                        )
                    }
                )
                is Prestador -> ChatResponse(
                    solicitacao.id,
                    solicitacao.contratante.nome,
                    null,
                    solicitacao.chat.map { mensagem ->
                        MensagemDto(
                            mensagem.idRemetente == usuario.id,
                            mensagem.horario,
                            mensagem.mensagem,
                            mensagem.anexo,
                        )
                    }
                )
                else -> return ResponseEntity.status(401).build()
            }
        }

        return ResponseEntity.status(if (chats.isEmpty()) 204 else 200).body(chats)

    }

    fun mandarMensagem(chatMensagemRequest: ChatMensagemRequest) {

        val usuario = jwtTokenManager.validateToken(chatMensagemRequest.token)
            ?: return

        val solicitacao = solicitacaoRepository.findById(chatMensagemRequest.solicitacaoId)

        if (solicitacao.isEmpty)
            return

//        val mensagem = Chat()
//        mensagem.solicitacao = solicitacao.get()
//        mensagem.mensagem = chatMensagemRequest.mensagem
//        mensagem.horario = Date()
//        mensagem.anexo = chatMensagemRequest.anexo
//        mensagem.idRemetente = usuario.id
//
//        chatRepository.save(mensagem)

        println(chatMensagemRequest)
    }
}