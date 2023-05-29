package manuall.newproject.dto.usuario

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

class UsuarioLoginRequest {

    @Schema(description = "E-mail do usuário", example = "joaquim.pires@sptech.school")
    @field:NotBlank
    @field:Email
    var email: String? = null

    @Schema(description = "Senha do usuário", example = "senha123")
    @field:NotBlank
    @field:Size(min = 8, max = 24)
    var senha: String? = null

    @Schema(description = "Tipo do usuário", example = "1")
    @field:NotNull
    @field:Min(1)
    @field:Max(3)
    var tipoUsuario: Int = 0
}