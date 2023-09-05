package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import manuall.api.domain.Usuario
import manuall.api.dto.perfil.*
import manuall.api.dto.usuario.AlterPfpRequest
import manuall.api.service.PerfilService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/perfil")
@CrossOrigin("http://localhost:5173")
class PerfilController(
    val perfilService: PerfilService
) {

    @PostMapping("/acessar/{idPrestador}")
    fun acessarPerfilPrestador(
        @PathVariable idPrestador: Int
    ): ResponseEntity<Unit> {
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
    ): ResponseEntity<PerfilDto> {
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

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/fotoPerfil")
    fun atualizarFotoPerfil(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody alterPfpRequest: AlterPfpRequest
    ): ResponseEntity<Usuario> {
        return perfilService.atualizarPFP(token, alterPfpRequest)
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/solicitacoes")
    fun getSolicitacoes(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String
    ): ResponseEntity<List<NotificacaoDto>> {
        return perfilService.getSolicitacoes(token)
    }

    @SecurityRequirement(name = "Bearer")
    @PostMapping("/postarUrl")
    fun postarUrl(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody urlPerfilDto: UrlPerfilDto
    ): ResponseEntity<List<String>> {

        return perfilService.postarUrl(token, urlPerfilDto)
    }

    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/excluirUrl")
    fun excluirUrl(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody urlPerfilDto: UrlPerfilDto
    ): ResponseEntity<Int> {

        return perfilService.excluirUrls(token, urlPerfilDto)
    }
}