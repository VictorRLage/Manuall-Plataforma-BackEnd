package manuall.api.controller

import manuall.api.domain.RecuperacaoSenha
import manuall.api.dto.resetsenha.Alterar
import manuall.api.dto.resetsenha.Email
import manuall.api.dto.resetsenha.Verificar
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
    fun verificarCodigo(@RequestBody request: Verificar): ResponseEntity<Void> {
        val email = request.email
        val codigo = request.codigo
        return recuperarSenhaService.verificarCodigo(email, codigo)
    }

    @PatchMapping("/alterarsenha")
    fun atualizarSenha(@RequestBody request: Alterar): ResponseEntity<Void> {
        val email = request.email
        val novaSenha = request.novaSenha
        recuperarSenhaService.alterarSenha(email, novaSenha)
        return ResponseEntity.ok().build()
    }
}
