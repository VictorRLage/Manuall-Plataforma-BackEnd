package manuall.api.websocket

import manuall.api.dto.mensagem.ChatMensagemRequest
import manuall.api.service.ChatService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MensagemWsController(
    val chatService: ChatService
) {
    @MessageMapping("/chat")
    fun mandarMensagem(
        @RequestBody chatMensagemRequest: ChatMensagemRequest
    ) {
        chatService.mandarMensagem(chatMensagemRequest)
    }

//    @MessageMapping("/visualizar")
}