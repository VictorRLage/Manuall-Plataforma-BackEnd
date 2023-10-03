package manuall.api.controller

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
    fun buscarHistorico(
        @RequestHeader("Authorization") token: String?,
    ): ResponseEntity<List<HistoricoDTO>> {
        return historicoService.buscarHistorico(token)
    }
}