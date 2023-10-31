package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.api.domain.Mensagem
import manuall.api.dto.mensagem.ChatResponse
import manuall.api.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat")
class ChatController (
    val chatService: ChatService
): DominiosBuscaveis<Mensagem> {

    override fun buscarTodos(token: String?): ResponseEntity<List<Mensagem>> {
        return chatService.buscarTodos(token)
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    fun getChats(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<List<ChatResponse>> {
        return chatService.getChats(token)
    }
}