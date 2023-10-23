package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.api.domain.Chat
import manuall.api.dto.chat.ChatResponse
import manuall.api.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat")
class ChatController (
    val chatService: ChatService
): DominiosBuscaveis<Chat> {

    override fun buscarTodos(token: String?): ResponseEntity<List<Chat>> {
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