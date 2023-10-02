package manuall.api.controller

import manuall.api.service.HistoricoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/historico")
class HistoricoController {

    @Autowired
    private lateinit var historicoService: HistoricoService

    @GetMapping("/buscarHistorico")
    fun buscarHistorico(
        @RequestHeader("Authorization") token: String,
        @RequestParam("usuario_id") usuario_id: Long
    ): ResponseEntity<Any> {
        val historico = historicoService.buscarHistorico(usuario_id)
        return ResponseEntity.ok(historico)
    }
}