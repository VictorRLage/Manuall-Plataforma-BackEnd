package manuall.newproject.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import manuall.newproject.dto.*
import manuall.newproject.dto.cadastro.*
import manuall.newproject.service.CadastroService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cadastrar")
@CrossOrigin("http://localhost:3000")
class CadastroController (
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
    ): ResponseEntity<Int> {
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
    fun cadastrar3Prest(
        @PathVariable @Schema(example = "1") id: Int,
        @RequestBody cadastrar3Dto: Cadastrar3Dto
    ): ResponseEntity<String> {
        return cadastroService.cadastrar3Prest(id, cadastrar3Dto)
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/4/{idPlano}")
    fun cadastrar4Prest(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String,
        @PathVariable @Schema(example = "1") idPlano: Int
    ): ResponseEntity<String> {
        return cadastroService.cadastrar4Prest(token, idPlano)
    }
}