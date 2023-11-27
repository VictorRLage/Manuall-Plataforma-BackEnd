package manuall.api.service

import jakarta.mail.internet.MimeMessage
import manuall.api.dto.resetsenha.Email
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailSenderService(private val emailSender: JavaMailSender) {

    fun sendMail(email: Email) {
        val msg = createSimpleMessage(email)
        emailSender.send(msg)
    }

    private fun createSimpleMessage(email: Email): MimeMessage {
        val message: MimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)

        setupMessage(helper, email)

        return message
    }

    private fun setupMessage(helper: MimeMessageHelper, email: Email) {
        helper.setTo(email.to)
        helper.setSubject(email.subject)
        helper.setText(email.text)
    }
}
