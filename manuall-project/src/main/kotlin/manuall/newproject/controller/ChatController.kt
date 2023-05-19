package manuall.newproject.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.newproject.dto.ChatMensagensResponse
import manuall.newproject.dto.ChatPegarDadosDestinatariosDto
import manuall.newproject.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
@CrossOrigin("http://localhost:3000")
class ChatController (
    val chatService: ChatService
) {

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    fun getChats(@RequestHeader("Authorization") token: String): ResponseEntity<List<ChatPegarDadosDestinatariosDto>> {
        return chatService.getChats(token)
    }

    @GetMapping("/{idSolicitacao}")
    @SecurityRequirement(name = "Bearer")
    fun getChatsByIdSolicitacao(@RequestHeader("Authorization") token: String, @PathVariable idSolicitacao: Int): ResponseEntity<ChatMensagensResponse> {
        return chatService.getChatsByIdSolicitacao(token, idSolicitacao)
    }

    @GetMapping("/{idSolicitacao}/{idUltimaMensagem}")
    @SecurityRequirement(name = "Bearer")
    fun getChatsByIdSolicitacaoIdUltimaMensagem(@RequestHeader("Authorization") token: String, @PathVariable idSolicitacao: Int, @PathVariable idUltimaMensagem: Int): ResponseEntity<ChatMensagensResponse> {
        TODO()
    }
}