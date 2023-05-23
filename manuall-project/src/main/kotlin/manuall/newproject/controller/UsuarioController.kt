package manuall.newproject.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import manuall.newproject.domain.Area
import manuall.newproject.domain.Servico
import manuall.newproject.domain.Usuario
import manuall.newproject.dto.*
import manuall.newproject.repository.*
import manuall.newproject.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("http://localhost:3000")
class UsuarioController (
    val usuarioService: UsuarioService
) {

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
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<Void> {
        return usuarioService.logoff(token)
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/senha")
    fun atualizarSenha(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody alterSenhaRequest: AlterSenhaRequest
    ): ResponseEntity<Usuario> {
        return usuarioService.atualizarSenha(token, alterSenhaRequest)
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/desc")
    fun atualizarDesc(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @RequestBody alterDescRequest: AlterDescRequest
    ): ResponseEntity<Usuario> {
        return usuarioService.atualizarDesc(token, alterDescRequest)
    }

    @Transactional
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/deletar")
    fun deletar(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String
    ): ResponseEntity<String> {
        return usuarioService.deletar(token)
    }

    @GetMapping("/cadastrar/prospect")
    fun checarProspect(
        @RequestBody prospectDTO: ProspectDTO
    ): ResponseEntity<PipefyReturnDTO>{ //DTO com todos os campos do Pipefy que precisam ser retornados
        return usuarioService.checarProspect(prospectDTO)
    }

    @PostMapping("/cadastrar/1")
    fun cadastrar1(
        @RequestBody @Valid cadastrar1DTO: Cadastrar1DTO
    ): ResponseEntity<Int> {
        return usuarioService.cadastrar1(cadastrar1DTO)
    }

    @PutMapping("/cadastrar/2/{id}")
    fun cadastrar2(
        @PathVariable @Schema(example = "1") id: Int,
        @RequestBody cadastrar2DTO: Cadastrar2DTO
    ): ResponseEntity<String> {
        return usuarioService.cadastrar2(id, cadastrar2DTO)
    }

    @GetMapping("/cadastrar/3/prestador/areas")
    fun buscarArea(): List<Area> {
        return usuarioService.buscarArea()
    }

    @GetMapping("/cadastrar/3/prestador/buscarServicos/{id}")
    fun buscarTiposServico(
        @PathVariable id:Int
    ): List<Servico> {
        return usuarioService.buscarTiposServico(id)
    }

    @PutMapping("/cadastrar/3/prestador/{id}")
    fun cadastrar3Prest(
        @PathVariable @Schema(example = "1") id:Int,
        @RequestBody cadastrar3PrestDTO:Cadastrar3PrestDTO
    ): ResponseEntity<String> {
        return usuarioService.cadastrar3Prest(id, cadastrar3PrestDTO)
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/cadastrar/4/prestador/{idPlano}")
    fun cadastrar4Prest(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @PathVariable idPlano:Int
    ): ResponseEntity<String> {
        return usuarioService.cadastrar4Prest(token, idPlano)
    }

    @GetMapping("prestadores/{idArea}/{filtro}/{crescente}")
    fun getPrestadoresFiltrado(
        @PathVariable idArea: String,
        @PathVariable filtro: String,
        @PathVariable crescente: Boolean
    ): ResponseEntity<List<UsuariosFilteredList>> {
        return usuarioService.getPrestadoresFiltrado(idArea, filtro, crescente)
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/perfil/prestador")
    fun checarPrestador(@RequestHeader("Authorization") @Schema(hidden = true) token: String): ResponseEntity<PerfilDTO> {
        return usuarioService.checarPrestador(token)
    }

    @PostMapping("/perfil/{idPrestador}/acessoConfirmado")
    fun acessarPerfilPrestador(@PathVariable idPrestador: Int): ResponseEntity<Void> {
        return usuarioService.acessarPerfilPrestador(idPrestador)
    }
}