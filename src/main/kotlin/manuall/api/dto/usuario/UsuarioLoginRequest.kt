package manuall.api.dto.usuario

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

class UsuarioLoginRequest {

    @Schema(description = "E-mail do usuário", example = "joaquim.pires@sptech.school")
    var email: String? = null

    @Schema(description = "Senha do usuário", example = "senha123")
    var senha: String? = null

    @Schema(description = "Tipo do usuário", example = "1")
    var tipoUsuario: Int? = null
}