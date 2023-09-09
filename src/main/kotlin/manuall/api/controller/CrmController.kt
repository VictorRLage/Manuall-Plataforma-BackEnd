package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.api.dto.crm.DadosContratanteCrm
import manuall.api.dto.crm.DadosPrestadorCrm
import manuall.api.service.CrmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/crm")
@CrossOrigin("http://localhost:5173")
class CrmController (
    val crmService: CrmService
): DominiosBuscaveis {

    override fun buscarTodos(token: String?): ResponseEntity<List<Any>> {
        return crmService.buscarTodos(token)
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    fun buscarMensagensManuel(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
    ): ResponseEntity<List<Int>> {
        return crmService.buscarMensagensManuel(token)
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    fun postarMensagemManuel(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestBody crmMsgs: List<Int>
    ): ResponseEntity<Unit> {
        return crmService.postarMensagemManuel(token, crmMsgs)
    }

    @GetMapping("/dados/1")
    @SecurityRequirement(name = "Bearer")
    fun buscarDadosContratante(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<DadosContratanteCrm> {
        return crmService.buscarDadosContratante(token)
    }

    @GetMapping("/dados/2")
    @SecurityRequirement(name = "Bearer")
    fun buscarDadosPrestador(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<DadosPrestadorCrm> {
        return crmService.buscarDadosPrestador(token)
    }
}