package manuall.newproject.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import manuall.newproject.dto.AlterDescRequest
import manuall.newproject.dto.AlterSenhaRequest
import manuall.newproject.dto.CadastroRequest
import manuall.newproject.dto.UsuarioLoginDto
import manuall.newproject.domain.Usuario
import manuall.newproject.repository.*
import manuall.newproject.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController (
    val usuarioService: UsuarioService
) {

    @PostMapping("/login")
    fun login(@RequestBody usuarioLoginDto: UsuarioLoginDto): ResponseEntity<String> {
        return usuarioService.login(usuarioLoginDto)

    }

    @SecurityRequirement(name = "Bearer")
    @PostMapping("/logoff")
    fun logoff(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        TODO() // ainda não temos a tabela para expirar token manualmente
    }

    @PostMapping("/cadastrar")
    fun criar(@RequestBody cadastroRequest: CadastroRequest): ResponseEntity<Void> {
        return usuarioService.criar(cadastroRequest)
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/senha")
    fun atualizarSenha(@RequestHeader("Authorization") token: String, @RequestBody alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario> {
        return usuarioService.atualizarSenha(token, alterSenhaRequest)
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/desc")
    fun atualizarDesc(@RequestHeader("Authorization") token: String, @RequestBody alterDescRequest: AlterDescRequest): ResponseEntity<Usuario> {
        return usuarioService.atualizarDesc(token, alterDescRequest)
    }

    @Transactional
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/deletar")
    fun deletar(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        return usuarioService.deletar(token)
    }
}