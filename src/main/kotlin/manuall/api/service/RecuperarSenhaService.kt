package manuall.api.service

import jakarta.mail.internet.MimeMessage
import manuall.api.domain.RecuperacaoSenha
import manuall.api.dto.resetsenha.Email
import manuall.api.repository.RecuperacaoSenhaRepository
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import kotlin.random.Random


@Service
class RecuperarSenhaService(
    private val emailSender: JavaMailSender,
    private val recuperacaoSenhaRepository: RecuperacaoSenhaRepository
) {

    fun sendMailWithRandomCode(email: Email) {
        val generatedCode = generateRandomCode()
        val message = createMessageWithCode(email.email, generatedCode)
        emailSender.send(message)

        saveToDatabase(email.email, generatedCode)
    }

    fun generateRandomCode(): String {
        return (100000 + Random.nextInt(900000)).toString()
    }

    fun createMessageWithCode(email: String, codigo: String): MimeMessage {
        val message: MimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message).apply {
            setTo(email)
            setSubject("Código de Recuperação de Senha")
            setText("Seu código de recuperação é: $codigo")
        }

        return message
    }

    fun saveToDatabase(email: String, codigo: String) {
        val recuperacaoSenha = RecuperacaoSenha().apply {
            this.email = email
            this.codigo = codigo
            this.dtEnvio = LocalDateTime.now()
        }

        recuperacaoSenhaRepository.save(recuperacaoSenha)
    }

    fun verificarCodigo(email: String, codigo: String): ResponseEntity<String> {
        val recuperacaoSenha = recuperacaoSenhaRepository.findByEmailAndCodigo(email, codigo)

        // Verificação 1: O token digitado é o mesmo do banco?
        if (recuperacaoSenha.codigo != codigo) {
            return ResponseEntity.status(400).body("Código incorreto")
        }

        // Verificação 2: O e-mail do banco é o mesmo e-mail que o usuário digitou no primeiro campo?
        if (recuperacaoSenha.email != email) {
            return ResponseEntity.status(400).body("E-mail incorreto")
        }

        val tempoDecorrido = Duration.between(recuperacaoSenha.dtEnvio, LocalDateTime.now())
        val minutosDecorridos = tempoDecorrido.toMinutes()

        if (minutosDecorridos >= 30) {
            return ResponseEntity.status(400).body("Código expirado")
        }

        // Se todas as verificações passarem, o código é válido
        return ResponseEntity.ok("Código válido")
    }

    @Transactional
    fun alterarSenha(email: String, novaSenha: String) {
        recuperacaoSenhaRepository.alterarSenha(email, novaSenha)
    }

}
