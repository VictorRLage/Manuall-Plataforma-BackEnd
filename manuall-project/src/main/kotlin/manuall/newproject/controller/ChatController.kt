package manuall.newproject.controller

import manuall.newproject.domain.Chat
import manuall.newproject.repository.ChatRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController (
    val chatRepository: ChatRepository
) {

    @GetMapping("/{id}")
    fun buscarChatPorId(@PathVariable id: Int): Chat? {
        return chatRepository.findById(id).orElseThrow()
    }
}