package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import manuall.api.dto.cadastro.*
import manuall.api.service.CadastroService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cadastrar")
class CadastroController(
    val cadastroService: CadastroService
) {

    @PostMapping("/prospect")
    fun checarProspect(
        @RequestBody prospectDTO: ProspectDto
    ): ResponseEntity<PipefyReturnDto> {
        return cadastroService.checarProspect(prospectDTO)
    }

    @PostMapping("/1")
    fun cadastrar1(
        @RequestBody @Valid cadastrar1DTO: Cadastrar1Dto
    ): ResponseEntity<Cad1Response> {
        return cadastroService.cadastrar1(cadastrar1DTO)
    }

    @PutMapping("/2/{id}")
    fun cadastrar2(
        @PathVariable @Schema(example = "1") id: Int,
        @RequestBody cadastrar2DTO: Cadastrar2Dto
    ): ResponseEntity<String> {
        return cadastroService.cadastrar2(id, cadastrar2DTO)
    }

    @PutMapping("/3/{id}")
    fun cadastrar3(
        @PathVariable @Schema(example = "1") id: Int,
        @RequestBody cadastrar3Dto: Cadastrar3Dto
    ): ResponseEntity<Unit> {
        return cadastroService.cadastrar3(id, cadastrar3Dto)
    }

    @PutMapping("/4/{idPlano}")
    @SecurityRequirement(name = "Bearer")
    fun cadastrar4(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @PathVariable @Schema(example = "1") idPlano: Int
    ): ResponseEntity<Unit> {
        return cadastroService.cadastrar4(token, idPlano)
    }

    @GetMapping("/1/{id}")
    fun getCad1Info(
        @PathVariable @Schema(example = "1") id: Int,
    ): ResponseEntity<Cad1InfoResponse> {
        return cadastroService.getCad1Info(id)
    }

    @GetMapping("/2/{id}")
    fun getCad2Info(
        @PathVariable @Schema(example = "1") id: Int,
    ): ResponseEntity<Cad2InfoResponse> {
        return cadastroService.getCad2Info(id)
    }
}