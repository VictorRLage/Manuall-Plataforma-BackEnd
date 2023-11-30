package manuall.api.controller

import manuall.api.dto.resetsenha.Email
import manuall.api.service.EmailSenderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/email")
class EmailSenderController(private val emailSenderService: EmailSenderService) {
    @PostMapping("/enviaremail")
    fun sendEmail(@RequestBody mail: Email): ResponseEntity<Void> {
        emailSenderService.sendMail(mail)
        return ResponseEntity.ok().build()
    }
}
