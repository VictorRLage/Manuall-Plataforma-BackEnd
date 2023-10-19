package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.api.dto.dashboard.HistoricoDTO
import manuall.api.service.HistoricoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/historico")
class HistoricoController(
    val historicoService: HistoricoService
) {

    @GetMapping("/buscarHistorico")
    @SecurityRequirement(name = "Bearer")
    fun buscarHistorico(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<List<HistoricoDTO>> {
        return historicoService.buscarHistorico(token)
    }
}