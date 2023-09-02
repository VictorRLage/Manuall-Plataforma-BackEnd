package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.api.dto.chat.ChatMensagemRequest
import manuall.api.dto.chat.ChatMensagensResponse
import manuall.api.dto.chat.ChatPegarDadosDestinatariosDto
import manuall.api.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat")
@CrossOrigin("http://localhost:5173")
class ChatController (
    val chatService: ChatService
) {

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    fun getChats(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String
    ): ResponseEntity<List<ChatPegarDadosDestinatariosDto>> {
        return chatService.getChats(token)
    }

    @GetMapping("/{idSolicitacao}")
    @SecurityRequirement(name = "Bearer")
    fun getMensagensByIdSolicitacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @PathVariable idSolicitacao: Int
    ): ResponseEntity<ChatMensagensResponse> {
        return chatService.getChatsByIdSolicitacao(token, idSolicitacao)
    }

    @GetMapping("/{idSolicitacao}/buscar/{idUltimaMensagem}")
    @SecurityRequirement(name = "Bearer")
    fun getChatsByIdSolicitacaoIdUltimaMensagem(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @PathVariable idSolicitacao: Int,
        @PathVariable idUltimaMensagem: Int
    ): ResponseEntity<ChatMensagensResponse> {
        return chatService.getBySolicitacaoIdWhereSolicitacaoIdHigherThan(token, idSolicitacao, idUltimaMensagem)
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    fun mandarMensagem(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody chatMensagemRequest: ChatMensagemRequest
    ): ResponseEntity<Int> {
        return chatService.mandarMensagem(token, chatMensagemRequest)
    }
}