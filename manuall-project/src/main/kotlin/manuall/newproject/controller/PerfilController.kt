package manuall.newproject.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import manuall.newproject.domain.Usuario
import manuall.newproject.dto.perfil.AlterDescRequest
import manuall.newproject.dto.perfil.AlterSenhaRequest
import manuall.newproject.dto.perfil.AlterarPerfilDto
import manuall.newproject.dto.perfil.PerfilDTO
import manuall.newproject.service.PerfilService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/perfil")
@CrossOrigin("http://localhost:3000")
class PerfilController (
    val perfilService: PerfilService
) {

    @PostMapping("/acessar/{idPrestador}")
    fun acessarPerfilPrestador(
        @PathVariable idPrestador: Int
    ): ResponseEntity<Void> {
        return perfilService.acessarPerfilPrestador(idPrestador)
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/alterar")
    fun alterarPerfil(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody alterarPerfilDTO: AlterarPerfilDto
    ): ResponseEntity<String> {
        return perfilService.alterarPerfil(token, alterarPerfilDTO)
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    fun checarPrestador(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String
    ): ResponseEntity<PerfilDTO> {
        return perfilService.checarPrestador(token)
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/senha")
    fun atualizarSenha(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody alterSenhaRequest: AlterSenhaRequest
    ): ResponseEntity<Usuario> {
        return perfilService.atualizarSenha(token, alterSenhaRequest)
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/desc")
    fun atualizarDesc(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody alterDescRequest: AlterDescRequest
    ): ResponseEntity<Usuario> {
        return perfilService.atualizarDesc(token, alterDescRequest)
    }

    @Transactional
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping
    fun deletar(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String
    ): ResponseEntity<String> {
        return perfilService.deletar(token)
    }
}