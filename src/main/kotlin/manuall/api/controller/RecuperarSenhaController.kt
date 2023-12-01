package manuall.api.controller

import manuall.api.domain.RecuperacaoSenha
import manuall.api.dto.resetsenha.Email
import manuall.api.service.RecuperarSenhaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/email")
class RecuperarSenhaController(private val recuperarSenhaService: RecuperarSenhaService) {
    @PostMapping("/enviaremail")
    fun sendEmail(@RequestBody mail: Email): ResponseEntity<Void> {
        recuperarSenhaService.sendMailWithRandomCode(mail)
        return ResponseEntity.ok().build()
    }
    @PostMapping("/verificar")
    fun verificarCodigo(email: String, @RequestParam codigo: String): ResponseEntity<Void> {
        recuperarSenhaService.verificarCodigo(email, codigo)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/alterarsenha")
    fun atualizarSenha(@RequestParam email: String, @RequestParam novaSenha: String): ResponseEntity<String> {
        recuperarSenhaService.alterarSenha(email, novaSenha)
        return ResponseEntity.ok("Senhas atualizadas com sucesso")
    }
}
