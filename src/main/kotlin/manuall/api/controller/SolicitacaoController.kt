package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import manuall.api.domain.Solicitacao
import manuall.api.dto.solicitacao.PostarAvaliacaoDto
import manuall.api.dto.solicitacao.OrcamentoDto
import manuall.api.dto.solicitacao.SolicitacaoDto
import manuall.api.service.SolicitacaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/solicitacao")
class SolicitacaoController (
    val solicitacaoService: SolicitacaoService
): DominiosBuscaveis<Solicitacao> {

    override fun buscarTodos(token: String?): ResponseEntity<List<Solicitacao>> {
        return solicitacaoService.buscarTodos(token)
    }

    @GetMapping("/servicos/{idPrestador}")
    fun getServicosPrestadorPorPrestador(
        @PathVariable idPrestador: Int
    ): ResponseEntity<List<Int>> {
        return solicitacaoService.getServicosPrestadorPorPrestador(idPrestador)
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    fun enviarSolicitacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody solicitacaoDto: SolicitacaoDto
    ): ResponseEntity<Unit> {
        return solicitacaoService.enviarSolicitacao(token, solicitacaoDto)
    }

    @PostMapping("/{idSolicitacao}/{aceitar}")
    @SecurityRequirement(name = "Bearer")
    fun responderSolicitacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @PathVariable idSolicitacao: Int,
        @PathVariable @Schema(example = "true") aceitar: Boolean
    ): ResponseEntity<Unit> {
        return solicitacaoService.responderSolicitacao(token, idSolicitacao, aceitar)
    }

    @PostMapping("/{idSolicitacao}/cancelar")
    @SecurityRequirement(name = "Bearer")
    fun cancelarSolicitacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @PathVariable idSolicitacao: Int
    ): ResponseEntity<Unit> {
        return solicitacaoService.cancelarSolicitacao(token, idSolicitacao)
    }

    @Transactional
    @DeleteMapping("/{idSolicitacao}")
    @SecurityRequirement(name = "Bearer")
    fun deletarSolicitacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @PathVariable idSolicitacao: Int
    ): ResponseEntity<Unit> {
        return solicitacaoService.deletarSolicitacao(token, idSolicitacao)
    }

    @PostMapping("/postarOrcamento")
    @SecurityRequirement(name = "Bearer")
    fun postarOrcamento(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody orcamentoDto: OrcamentoDto
    ): ResponseEntity<Int> {
        return solicitacaoService.enviarOrcamento(token, orcamentoDto)
    }

    @PostMapping("/postarAvaliacao")
    @SecurityRequirement(name = "Bearer")
    fun postarAvaliacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody postarAvaliacaoDTO: PostarAvaliacaoDto
    ): ResponseEntity<Int> {
        return solicitacaoService.enviarAvaliacao(token, postarAvaliacaoDTO)
    }
}