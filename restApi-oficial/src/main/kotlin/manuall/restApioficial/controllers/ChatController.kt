package manuall.restApioficial.controllers

import manuall.restApioficial.models.Chat
import manuall.restApioficial.repositories.ChatRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/chat")
class ChatController (
    val chatRepository: ChatRepository
) {

    @GetMapping("/{id}")
    fun buscarChatPorId(@PathVariable id: Int): Chat? {
        return chatRepository.findById(id).orElseThrow()
    }
}