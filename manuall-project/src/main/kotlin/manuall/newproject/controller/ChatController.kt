package manuall.newproject.controller

import manuall.newproject.service.ChatService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController (
    val chatService: ChatService
) {
}