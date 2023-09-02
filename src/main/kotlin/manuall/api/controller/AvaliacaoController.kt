package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.api.dto.avaliacao.PostarAvaliacaoDto
import manuall.api.service.AvaliacaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliacao")
@CrossOrigin("http://localhost:5173")
class AvaliacaoController(
    val avaliacaoService: AvaliacaoService
) {

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    fun postarAvaliacao(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody postarAvaliacaoDTO: PostarAvaliacaoDto
    ): ResponseEntity<Int> {
        return avaliacaoService.postarAvaliacao(token, postarAvaliacaoDTO)
    }
}