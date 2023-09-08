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
): DominiosBuscaveis {

    override fun buscarTodos(token: String?): ResponseEntity<List<Any>> {
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
    ): ResponseEntity<Usuario> {
        return perfilService.atualizarSenha(token, alterSenhaRequest)
    }

    @PatchMapping("/alterar/desc")
    @SecurityRequirement(name = "Bearer")
    fun atualizarDesc(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody alterDescRequest: AlterDescRequest
    ): ResponseEntity<Usuario> {
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

    @PatchMapping("/alterar/fotoPerfil")
    @SecurityRequirement(name = "Bearer")
    fun atualizarFotoPerfil(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody alterPfpRequest: AlterPfpRequest
    ): ResponseEntity<Usuario> {
        return perfilService.atualizarPFP(token, alterPfpRequest)
    }

    @GetMapping("/solicitacoes")
    @SecurityRequirement(name = "Bearer")
    fun getSolicitacoes(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<List<NotificacaoDto>> {
        return perfilService.getSolicitacoes(token)
    }

    @PostMapping("/postarUrl")
    @SecurityRequirement(name = "Bearer")
    fun postarUrl(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody urlPerfilDto: UrlPerfilDto
    ): ResponseEntity<List<String>> {

        return perfilService.postarUrl(token, urlPerfilDto)
    }

    @DeleteMapping("/excluirUrl")
    @SecurityRequirement(name = "Bearer")
    fun excluirUrl(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody urlPerfilDto: UrlPerfilDto
    ): ResponseEntity<Int> {

        return perfilService.excluirUrls(token, urlPerfilDto)
    }
}