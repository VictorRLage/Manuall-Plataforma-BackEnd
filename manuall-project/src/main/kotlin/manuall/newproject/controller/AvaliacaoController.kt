package manuall.newproject.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import manuall.newproject.dto.PostarAvaliacaoDTO
import manuall.newproject.dto.UsuarioLoginCheckRequest
import manuall.newproject.service.AvaliacaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin("http://localhost:3000")
data class AvaliacaoController(
    val avaliacaoService: AvaliacaoService
) {
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/postar")
    fun loginChecar(
        @RequestHeader("Authorization") token: String,
        @RequestBody @Valid postarAvaliacaoDTO: PostarAvaliacaoDTO
    ): ResponseEntity<Int> {
        return avaliacaoService.postarAvaliacao(token, postarAvaliacaoDTO)
    }
}