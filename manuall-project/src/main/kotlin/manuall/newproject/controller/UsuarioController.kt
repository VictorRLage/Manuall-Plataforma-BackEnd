package manuall.newproject.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import manuall.newproject.domain.Area
import manuall.newproject.domain.Servico
import manuall.newproject.domain.Usuario
import manuall.newproject.dto.*
import manuall.newproject.dto.usuario.AprovacaoDto
import manuall.newproject.dto.usuario.UsuarioLoginCheckRequest
import manuall.newproject.dto.usuario.UsuarioLoginRequest
import manuall.newproject.dto.usuario.UsuariosFilteredList
import manuall.newproject.repository.*
import manuall.newproject.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/usuario")
@CrossOrigin("http://localhost:3000")
class UsuarioController(
    val usuarioService: UsuarioService
) {

    @GetMapping("/prestadores")
    fun getPrestadoresOrderByPlano(): ResponseEntity<List<UsuariosFilteredList>> {
        return usuarioService.getPrestadoresOrderByPlano()
    }

    @GetMapping("/prestadores/{idArea}")
    fun getPrestadoresByAreaIdOrderByPlano(
        @PathVariable idArea: Int
    ): ResponseEntity<List<UsuariosFilteredList>> {
        return usuarioService.getPrestadoresByAreaIdOrderByPlano(idArea)
    }

    @GetMapping("prestadores/{idArea}/{filtro}/{crescente}")
    fun getPrestadoresFiltrado(
        @PathVariable idArea: String,
        @PathVariable filtro: String,
        @PathVariable crescente: Boolean
    ): ResponseEntity<List<UsuariosFilteredList>> {
        return usuarioService.getPrestadoresFiltrado(idArea, filtro, crescente)
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

    @SecurityRequirement(name = "Bearer")
    @PostMapping("/logoff")
    fun logoff(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String
    ): ResponseEntity<Void> {
        return usuarioService.logoff(token)
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/login/checar/validade")
    fun checarValidadeLogin(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String
    ): ResponseEntity<Int> {
        return usuarioService.checarValidadeLogin(token)
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/aprovacoesPendentes")
    fun aprovacoesPendentes(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String
    ): ResponseEntity<List<AprovacaoDto>> {
        return usuarioService.aprovacoesPendentes(token)
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/aprovacoesPendentes/{idPrestador}/{aprovar}")
    fun aprovar(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @PathVariable idPrestador: Int,
        @PathVariable aprovar: Boolean
    ): ResponseEntity<Void> {
        return usuarioService.aprovar(token, idPrestador, aprovar)
    }

}