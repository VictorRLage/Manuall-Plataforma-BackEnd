package manuall.newproject.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.newproject.dto.ChatMensagemRequest
import manuall.newproject.dto.ChatMensagensResponse
import manuall.newproject.dto.ChatPegarDadosDestinatariosDto
import manuall.newproject.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat")
@CrossOrigin("http://localhost:3000")
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