package manuall.newproject.service

import manuall.newproject.domain.Chat
import manuall.newproject.domain.Contratante
import manuall.newproject.domain.Prestador
import manuall.newproject.dto.chat.*
import manuall.newproject.repository.ChatRepository
import manuall.newproject.repository.SolicitacaoRepository
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ChatService (
    val chatRepository: ChatRepository,
    val solicitacaoRepository: SolicitacaoRepository,
    val jwtTokenManager: JwtTokenManager
) {

    fun getChats(token: String): ResponseEntity<List<ChatPegarDadosDestinatariosDto>> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        val chats = when (usuarioEncontrado) {
            is Contratante -> solicitacaoRepository.getPrestadoresByContratanteUsuarioId(usuarioEncontrado.id)
            is Prestador -> solicitacaoRepository.getContratantesByPrestadorUsuarioId(usuarioEncontrado.id)
            else -> arrayListOf()
        }

        return ResponseEntity.status(if (chats.isEmpty()) 204 else 200).body(chats)

    }

    fun getChatsByIdSolicitacao(token: String, idSolicitacao: Int): ResponseEntity<ChatMensagensResponse> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        if (solicitacaoRepository.findById(idSolicitacao).isEmpty) {
            return ResponseEntity.status(404).build()
        }

        val destinatario: ChatPegarDadosDestinatarioDto
        val mensagens: List<ChatMensagemResponse>

        if (usuarioEncontrado is Contratante) {
            destinatario = solicitacaoRepository.getDadosPrestadorById(idSolicitacao).get()
            mensagens = chatRepository.getMsgsByUsuarioIdAndSolicitacaoIdContratante(usuarioEncontrado.id, idSolicitacao)
        } else {
            destinatario = solicitacaoRepository.getDadosContratanteById(idSolicitacao).get()
            mensagens = chatRepository.getMsgsByUsuarioIdAndSolicitacaoIdPrestador(usuarioEncontrado.id, idSolicitacao)
        }

        return ResponseEntity.status(if (mensagens.isEmpty()) 204 else 200).body(
            ChatMensagensResponse(
                destinatario,
                mensagens
            )
        )
    }

    fun getBySolicitacaoIdWhereSolicitacaoIdHigherThan(token: String, idSolicitacao: Int, idUltimaMensagem: Int): ResponseEntity<ChatMensagensResponse> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        if (solicitacaoRepository.findById(idSolicitacao).isEmpty) {
            return ResponseEntity.status(404).build()
        }

        val destinatario: ChatPegarDadosDestinatarioDto
        val mensagens: List<ChatMensagemResponse>

        if (usuarioEncontrado is Contratante) {
            destinatario = solicitacaoRepository.getDadosPrestadorById(idSolicitacao).get()
            mensagens = chatRepository.getBySolicitacaoIdWhereSolicitacaoIdHigherThanContratante(usuarioEncontrado.id, idSolicitacao, idUltimaMensagem)
        } else {
            destinatario = solicitacaoRepository.getDadosContratanteById(idSolicitacao).get()
            mensagens = chatRepository.getBySolicitacaoIdWhereSolicitacaoIdHigherThanPrestador(usuarioEncontrado.id, idSolicitacao, idUltimaMensagem)
        }

        return ResponseEntity.status(if (mensagens.isEmpty()) 204 else 200).body(
            ChatMensagensResponse(
            destinatario,
            mensagens
        )
        )
    }

    fun mandarMensagem(token: String, chatMensagemRequest: ChatMensagemRequest): ResponseEntity<Int> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        if (solicitacaoRepository.findById(chatMensagemRequest.idSolicitacao).isEmpty) {
            return ResponseEntity.status(404).build()
        }

        val mensagem = Chat()
        mensagem.solicitacao = solicitacaoRepository.findById(chatMensagemRequest.idSolicitacao).get()
        mensagem.mensagem = chatMensagemRequest.mensagem
        mensagem.horario = chatMensagemRequest.horario
        mensagem.anexo = chatMensagemRequest.anexo
        mensagem.idRemetente = usuarioEncontrado.id

        return ResponseEntity.status(201).body(chatRepository.save(mensagem).id)
    }
}