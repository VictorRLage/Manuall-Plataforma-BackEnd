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
class PerfilController(
    val perfilService: PerfilService
): DominiosBuscaveis<Usuario> {

    override fun buscarTodos(token: String?): ResponseEntity<List<Usuario>> {
        return perfilService.buscarTodos(token)
    }

    @PostMapping("/acessar/{idPrestador}")
    fun acessarPerfilPrestador(
        @PathVariable idPrestador: Int
    ): ResponseEntity<Unit> {
        return perfilService.acessarPerfilPrestador(idPrestador)
    }

    @PutMapping("/alterar")
    @SecurityRequirement(name = "Bearer")
    fun alterarPerfil(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody alterarPerfilDTO: AlterarPerfilDto
    ): ResponseEntity<String> {
        return perfilService.alterarPerfil(token, alterarPerfilDTO)
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    fun checarPrestador(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<PerfilDto> {
        return perfilService.checarPrestadorByToken(token)
    }

    @GetMapping("/{id}")
    fun checarPrestador(
        @PathVariable id: Int
    ): ResponseEntity<PerfilDto> {
        return perfilService.checarPrestadorById(id)
    }

    @PatchMapping("/alterar/senha")
    @SecurityRequirement(name = "Bearer")
    fun atualizarSenha(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody alterSenhaRequest: AlterSenhaRequest
    ): ResponseEntity<Unit> {
        return perfilService.atualizarSenha(token, alterSenhaRequest)
    }

    @PatchMapping("/alterar/desc")
    @SecurityRequirement(name = "Bearer")
    fun atualizarDesc(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody alterDescRequest: AlterDescRequest
    ): ResponseEntity<Unit> {
        return perfilService.atualizarDesc(token, alterDescRequest)
    }

    @Transactional
    @DeleteMapping
    @SecurityRequirement(name = "Bearer")
    fun deletar(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<String> {
        return perfilService.deletar(token)
    }

    @GetMapping("/fotoPerfil")
    @SecurityRequirement(name = "Bearer")
    fun getPfp(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<String> {
        return perfilService.getPfp(token)
    }

    @PatchMapping("/alterar/fotoPerfil")
    @SecurityRequirement(name = "Bearer")
    fun atualizarFotoPerfil(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody alterPfpRequest: AlterPfpRequest
    ): ResponseEntity<Unit> {
        return perfilService.atualizarPFP(token, alterPfpRequest)
    }

    @PostMapping("/imagem")
    @SecurityRequirement(name = "Bearer")
    fun postarUrl(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody anexo: UrlImagemDto
    ): ResponseEntity<Unit> {
        return perfilService.postarUrl(token, anexo)
    }

    @PostMapping("/imagem/delete/{imagemId}")
    @SecurityRequirement(name = "Bearer")
    fun excluirUrl(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @PathVariable imagemId: Int,
    ): ResponseEntity<Unit> {
        return perfilService.excluirUrls(token, imagemId)
    }
}