package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.api.dto.crm.DadosClienteCrm
import manuall.api.dto.crm.NovoCrmLog
import manuall.api.service.CrmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/crm")
@CrossOrigin("http://localhost:5173")
class CrmController (
    val crmService: CrmService
) {

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    fun buscarMensagensManuel(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
    ): ResponseEntity<String> {
        return crmService.buscarMensagensManuel(token)
    }

    @GetMapping("/dados")
    @SecurityRequirement(name = "Bearer")
    fun buscarDadosCliente(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String
    ): ResponseEntity<DadosClienteCrm> {
        return crmService.buscarDadosCliente(token)
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    fun postarMensagemManuel(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody novoCrmLog: NovoCrmLog
    ): ResponseEntity<Unit> {
        return crmService.postarMensagemManuel(token, novoCrmLog)
    }
}