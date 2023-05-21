package manuall.newproject.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.newproject.domain.UsuarioServico
import manuall.newproject.dto.SolicitacaoDto
import manuall.newproject.service.SolicitacaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/solicitacoes")
@CrossOrigin("http://localhost:3000")
data class SolicitacaoController (
    val solicitacaoService: SolicitacaoService
) {

    @GetMapping("/servicos/{idPrestador}")
    fun getServicosPrestadorPorPrestador(
        @PathVariable idPrestador: Int
    ): ResponseEntity<List<Int>> {
        return solicitacaoService.getServicosPrestadorPorPrestador(idPrestador)
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    fun enviarSolicitacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody solicitacaoDto: SolicitacaoDto
    ): ResponseEntity<Void> {
        return solicitacaoService.enviarSolicitacao(token, solicitacaoDto)
    }

    @PostMapping("/{idSolicitacao}/{aceitar}")
    @SecurityRequirement(name = "Bearer")
    fun responderSolicitacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @PathVariable idSolicitacao: Int,
        @PathVariable @Schema(example = "true") aceitar: Boolean
    ): ResponseEntity<Void> {
        return solicitacaoService.responderSolicitacao(token, idSolicitacao, aceitar)
    }

    @PostMapping("/{idSolicitacao}/cancelar")
    @SecurityRequirement(name = "Bearer")
    fun cancelarSolicitacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @PathVariable idSolicitacao: Int
    ): ResponseEntity<Void> {
        return solicitacaoService.cancelarSolicitacao(token, idSolicitacao)
    }
}