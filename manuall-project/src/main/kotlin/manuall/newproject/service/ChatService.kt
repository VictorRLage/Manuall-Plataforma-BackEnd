package manuall.newproject.service

import manuall.newproject.dto.ChatMensagensResponse
import manuall.newproject.dto.ChatPegarDadosDestinatariosDto
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

        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token)
        } else {
            return ResponseEntity.status(401).build()
        }

        val chats = when (usuarioEncontrado.tipoUsuario) {
            1 -> solicitacaoRepository.getPrestadoresByContratanteUsuarioId(usuarioEncontrado.id)
            2 -> solicitacaoRepository.getContratantesByPrestadorUsuarioId(usuarioEncontrado.id)
            else -> arrayListOf()
        }

        return ResponseEntity.status(if (chats.isEmpty()) 204 else 200).body(chats)

    }

    fun getChatsByIdSolicitacao(token: String, idSolicitacao: Int): ResponseEntity<ChatMensagensResponse> {

        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token)
        } else {
            return ResponseEntity.status(401).build()
        }

        val destinatario = if (usuarioEncontrado.tipoUsuario == 1) {
            solicitacaoRepository.getDadosPrestadorById(idSolicitacao).get()
        } else {
            solicitacaoRepository.getDadosContratanteById(idSolicitacao).get()
        }

        val mensagens = chatRepository.getBySolicitacaoId(idSolicitacao)

        return ResponseEntity.status(if (mensagens.isEmpty()) 204 else 200).body(ChatMensagensResponse(
            destinatario,
            mensagens
        ))
    }
}