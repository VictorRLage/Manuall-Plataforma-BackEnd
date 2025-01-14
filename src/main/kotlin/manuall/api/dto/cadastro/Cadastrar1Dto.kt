package manuall.api.dto.cadastro

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*
import org.hibernate.validator.constraints.br.CPF

data class Cadastrar1Dto (

    @Schema(example = "Fulano de Tal")
    @field:NotBlank
    val nome:String,

    @Schema(example = "fulano@example.com")
    @field:NotBlank
    @field:Email
    val email:String,

    @Schema(example = "39003147051")
    @field:CPF
    val cpf:String,

    @Schema(example = "9522925712")
    @field:NotBlank
    val telefone:String,

    @Schema(example = "urubu100")
    val senha:String,

    @Schema(example = "1")
    @field:NotNull
    @field:Min(1)
    @field:Max(2)
    val tipoUsuario:Int,

    val isReturning: Boolean,
    val id: Int?
)