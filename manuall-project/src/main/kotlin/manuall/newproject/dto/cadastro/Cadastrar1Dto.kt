package manuall.newproject.dto.cadastro

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
    @field:NotBlank
    @field:Size(min = 8, max = 24)
    val senha:String,

    @Schema(example = "1")
    @field:NotNull
    @field:Min(1)
    @field:Max(3)
    val tipoUsuario:Int
)