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

@Service
class ChatService (
    val chatRepository: ChatRepository,
    val solicitacaoRepository: SolicitacaoRepository,
    val jwtTokenManager: JwtTokenManager
) {

    fun buscarTodos(token: String?): ResponseEntity<List<Any>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(chatRepository.findAll())
    }

    fun getChats(token: String?): ResponseEntity<List<ChatPegarDadosDestinatariosDto>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val chats = when (usuario) {
            is Contratante -> solicitacaoRepository.getPrestadoresByContratanteUsuarioId(usuario.id)
            is Prestador -> solicitacaoRepository.getContratantesByPrestadorUsuarioId(usuario.id)
            else -> arrayListOf()
        }

        return ResponseEntity.status(if (chats.isEmpty()) 204 else 200).body(chats)

    }

    fun getChatsByIdSolicitacao(token: String?, idSolicitacao: Int): ResponseEntity<ChatMensagensResponse> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (solicitacaoRepository.findById(idSolicitacao).isEmpty)
            return ResponseEntity.status(404).build()

        val destinatario: ChatPegarDadosDestinatarioDto
        val mensagens: List<ChatMensagemResponse>

        if (usuario is Contratante) {
            destinatario = solicitacaoRepository.getDadosPrestadorById(idSolicitacao).get()
            mensagens = chatRepository.getMsgsByUsuarioIdAndSolicitacaoIdContratante(usuario.id, idSolicitacao)
        } else {
            destinatario = solicitacaoRepository.getDadosContratanteById(idSolicitacao).get()
            mensagens = chatRepository.getMsgsByUsuarioIdAndSolicitacaoIdPrestador(usuario.id, idSolicitacao)
        }

        return ResponseEntity.status(if (mensagens.isEmpty()) 204 else 200).body(
            ChatMensagensResponse(
                destinatario,
                mensagens
            )
        )
    }

    fun getBySolicitacaoIdWhereSolicitacaoIdHigherThan(token: String?, idSolicitacao: Int, idUltimaMensagem: Int): ResponseEntity<ChatMensagensResponse> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (solicitacaoRepository.findById(idSolicitacao).isEmpty) {
            return ResponseEntity.status(404).build()
        }

        val destinatario: ChatPegarDadosDestinatarioDto
        val mensagens: List<ChatMensagemResponse>

        if (usuario is Contratante) {
            destinatario = solicitacaoRepository.getDadosPrestadorById(idSolicitacao).get()
            mensagens = chatRepository.getBySolicitacaoIdWhereSolicitacaoIdHigherThanContratante(usuario.id, idSolicitacao, idUltimaMensagem)
        } else {
            destinatario = solicitacaoRepository.getDadosContratanteById(idSolicitacao).get()
            mensagens = chatRepository.getBySolicitacaoIdWhereSolicitacaoIdHigherThanPrestador(usuario.id, idSolicitacao, idUltimaMensagem)
        }

        return ResponseEntity.status(if (mensagens.isEmpty()) 204 else 200).body(
            ChatMensagensResponse(
                destinatario,
                mensagens
            )
        )
    }

    fun mandarMensagem(token: String?, chatMensagemRequest: ChatMensagemRequest): ResponseEntity<Int> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (solicitacaoRepository.findById(chatMensagemRequest.idSolicitacao).isEmpty) {
            return ResponseEntity.status(404).build()
        }

        val mensagem = Chat()
        mensagem.solicitacao = solicitacaoRepository.findById(chatMensagemRequest.idSolicitacao).get()
        mensagem.mensagem = chatMensagemRequest.mensagem
        mensagem.horario = chatMensagemRequest.horario
        mensagem.anexo = chatMensagemRequest.anexo
        mensagem.idRemetente = usuario.id

        return ResponseEntity.status(201).body(chatRepository.save(mensagem).id)
    }
}