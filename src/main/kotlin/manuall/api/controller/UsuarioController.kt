package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import manuall.api.domain.Area
import manuall.api.domain.Servico
import manuall.api.dto.usuario.*
import manuall.api.service.UsuarioService
import manuall.api.specification.UsuarioSpecification
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuario")
@CrossOrigin("http://localhost:5173")
class UsuarioController(
    val usuarioService: UsuarioService,
    val usuarioSpecification: UsuarioSpecification
) {

    @GetMapping("/id")
    @SecurityRequirement(name = "Bearer")
    fun getIdByToken(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<Int> {
        return usuarioService.getIdByToken(token)
    }

    @GetMapping("prestadores/{idArea}/{filtro}/{crescente}")
    fun getPrestadores(
        @PathVariable idArea: String,
        @PathVariable filtro: String,
        @PathVariable crescente: Boolean
    ): ResponseEntity<List<PrestadorCardDto>> {
        return ResponseEntity.status(200).body(
            usuarioSpecification.filtrar(
                idArea.toInt(),
                filtro,
                crescente
            )
        )
    }

    @GetMapping("/areas")
    fun buscarArea(): List<Area> {
        return usuarioService.buscarArea()
    }

    @GetMapping("/servico/{idServico}")
    fun buscarTiposServico(
        @PathVariable idServico: Int
    ): List<Servico> {
        return usuarioService.buscarTiposServico(idServico)
    }

    @PostMapping("/login/checar")
    fun loginChecar(
        @RequestBody @Valid usuarioLoginCheckRequest: UsuarioLoginCheckRequest
    ): ResponseEntity<Int> {
        return usuarioService.loginChecar(usuarioLoginCheckRequest.email)
    }

    @PostMapping("/login/efetuar")
    fun loginEfetuar(
        @RequestBody @Valid usuarioLoginRequest: UsuarioLoginRequest
    ): ResponseEntity<String> {
        return usuarioService.loginEfetuar(usuarioLoginRequest)
    }

    @PostMapping("/logoff")
    @SecurityRequirement(name = "Bearer")
    fun logoff(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<Unit> {
        return usuarioService.logoff(token)
    }

    @GetMapping("/login/checar/validade")
    @SecurityRequirement(name = "Bearer")
    fun checarValidadeLoginAdm(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<Unit> {
        return usuarioService.checarValidadeLoginAdm(token)
    }

    @GetMapping("/aprovacoesPendentes")
    @SecurityRequirement(name = "Bearer")
    fun aprovacoesPendentes(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<List<AprovacaoDto>> {
        return usuarioService.aprovacoesPendentes(token)
    }

    @GetMapping("/aprovacoesPendentes/{idPrestador}/{aprovar}")
    @SecurityRequirement(name = "Bearer")
    fun aprovar(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @PathVariable idPrestador: Int,
        @PathVariable aprovar: Boolean
    ): ResponseEntity<Unit> {
        return usuarioService.aprovar(token, idPrestador, aprovar)
    }

}