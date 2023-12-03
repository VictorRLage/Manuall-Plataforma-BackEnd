package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import manuall.api.domain.Area
import manuall.api.domain.Servico
import manuall.api.dto.perfil.NotificacaoDto
import manuall.api.dto.usuario.*
import manuall.api.service.UsuarioService
import manuall.api.specification.UsuarioSpecification
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream

@RestController
@RequestMapping("/usuario")
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

    @GetMapping("/prestadores/{idArea}/{filtro}/{crescente}/{tipo}")
    fun getPrestadores(
        @PathVariable idArea: Int,
        @PathVariable filtro: String,
        @PathVariable crescente: Boolean,
        @PathVariable tipo: String
    ): ResponseEntity<List<PrestadorCardDto>> {
        return ResponseEntity.status(200).body(
            usuarioSpecification.filtrar(
                idArea,
                filtro,
                crescente,
                tipo
            )
        )
    }

    @GetMapping("/areas")
    fun buscarArea(): List<Area> {
        return usuarioService.buscarArea()
    }

    @GetMapping("/nome")
    @SecurityRequirement(name = "Bearer")
    fun buscarNomeUsuario(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<String?> {
        return usuarioService.buscarNomeUsuario(token)
    }

    @GetMapping("/plano")
    @SecurityRequirement(name = "Bearer")
    fun buscarPlanoUsuario(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<Int?> {
        return usuarioService.buscarPlanoUsuario(token)
    }

    @GetMapping("/servico/{idServico}")
    fun buscarTiposServico(
        @PathVariable idServico: Int
    ): List<Servico> {
        return usuarioService.buscarTiposServico(idServico)
    }

    @PostMapping("/login/checar")
    fun loginChecar(
        @RequestBody usuarioLoginCheckRequest: UsuarioLoginCheckRequest
    ): ResponseEntity<List<Int>> {
        return usuarioService.loginChecar(usuarioLoginCheckRequest.email)
    }

    @PostMapping("/login/efetuar")
    fun loginEfetuar(
        @RequestBody @Valid usuarioLoginRequest: UsuarioLoginRequest
    ): ResponseEntity<LoginResponse> {
        return usuarioService.loginEfetuar(usuarioLoginRequest)
    }

    @GetMapping("/login/checar/validade")
    @SecurityRequirement(name = "Bearer")
    fun checarValidadeLoginAdm(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<Unit> {
        return usuarioService.checarValidadeLoginAdm(token)
    }

    @PostMapping("/logoff")
    @SecurityRequirement(name = "Bearer")
    fun logoff(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<Unit> {
        return usuarioService.logoff(token)
    }

    @GetMapping("/notificacoes")
    @SecurityRequirement(name = "Bearer")
    fun getNotificacoes(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<List<NotificacaoDto>> {
        return usuarioService.getNotificacoes(token)
    }

    @GetMapping("/aprovacoesPendentes")
    @SecurityRequirement(name = "Bearer")
    fun aprovacoesPendentes(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<List<AprovacaoDto>> {
        return usuarioService.aprovacoesPendentes(token)
    }

    @GetMapping("/aprovacoesPendentes/csv")
    @SecurityRequirement(name = "Bearer")
    fun gerarCsvAprovacoesPendentes(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<Resource> {
        val aprovacoes = usuarioService.aprovacoesPendentes(token).body ?: return ResponseEntity.noContent().build()
        val nomeArquivo = "aprovacoes_pendentes.csv"
        usuarioService.gravarCsvAprovacoes(aprovacoes, nomeArquivo)
        val arquivo = File(nomeArquivo)

        val resource: Resource = InputStreamResource(FileInputStream(arquivo))

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$nomeArquivo\"")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource)
    }

    @GetMapping("/aprovacoesPendentes/txt")
    @SecurityRequirement(name = "Bearer")
    fun gerarTxtAprovacoesPendentes(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?
    ): ResponseEntity<Resource> {
        val aprovacoes = usuarioService.aprovacoesPendentes(token).body ?: return ResponseEntity.noContent().build()
        val nomeArquivo = "aprovacoes_pendentes.txt"
        usuarioService.gravarTxtAprovacoes(aprovacoes, nomeArquivo)
        val arquivo = File(nomeArquivo)

        val resource: Resource = InputStreamResource(FileInputStream(arquivo))

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$nomeArquivo\"")
            .contentType(MediaType.parseMediaType("text/txt"))
            .body(resource)
    }

    @PostMapping("/aprovacoesPendentes/csv", consumes = ["multipart/form-data"])
    @SecurityRequirement(name = "Bearer")
    fun atualizarAprovacoesViaCsv(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestParam("arquivo") arquivo: MultipartFile
    ): ResponseEntity<Unit> {
        if (!arquivo.isEmpty) {
            usuarioService.atualizarAprovacoesViaCsv(arquivo.inputStream)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(400).build()
    }

    @PostMapping("/aprovacoesPendentes/txt", consumes = ["multipart/form-data"])
    @SecurityRequirement(name = "Bearer")
    fun atualizarAprovacoesViaTxt(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @RequestParam("arquivo") arquivo: MultipartFile
    ): ResponseEntity<Unit> {
        if (!arquivo.isEmpty) {
            usuarioService.atualizarAprovacoesViaTxt(arquivo.inputStream)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(400).build()
    }

    @PatchMapping("/aprovacoesPendentes/{idPrestador}/{aprovar}")
    @SecurityRequirement(name = "Bearer")
    fun aprovar(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @PathVariable idPrestador: Int,
        @PathVariable aprovar: Int
    ): ResponseEntity<Unit> {
        return usuarioService.aprovar(token, idPrestador, aprovar)
    }

    @PatchMapping("/aprovacoesPendentes/alterarStatusProcesso/{idPrestador}/{statusProcesso}")
    fun alterarStatusProcesso(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @PathVariable idPrestador: Int,
        @PathVariable statusProcesso: Int
    ): ResponseEntity<Unit> {
        return usuarioService.alterarStatusProcesso(token, idPrestador, statusProcesso)
    }

}