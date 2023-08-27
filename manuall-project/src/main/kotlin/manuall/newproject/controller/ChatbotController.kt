package manuall.newproject.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.newproject.dto.chatbot.NovoCrmLog
import manuall.newproject.service.ChatbotService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chatbot")
@CrossOrigin("http://localhost:5173")
class ChatbotController (
    val chatbotService: ChatbotService
) {

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    fun buscarMensagensManuel(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
    ): ResponseEntity<String> {
        return chatbotService.buscarMensagensManuel(token)
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    fun postarMensagemManuel(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody novoCrmLog: NovoCrmLog
    ): ResponseEntity<Unit> {
        return chatbotService.postarMensagemManuel(token, novoCrmLog)
    }
}