package manuall.api.websocket

import manuall.api.dto.chat.ChatMensagemRequest
import manuall.api.service.ChatService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatWsController(
    val chatService: ChatService
) {
    @MessageMapping("/chat")
    fun mandarMensagem(
        @RequestBody chatMensagemRequest: ChatMensagemRequest
    ) {
        chatService.mandarMensagem(chatMensagemRequest)
    }
}