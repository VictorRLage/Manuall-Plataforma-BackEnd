package manuall.newproject.dto

import io.swagger.v3.oas.annotations.media.Schema

class UsuarioLoginDto {

    @Schema(description = "E-mail do usuário", example = "fulano@example.com")
    var email: String? = null

    @Schema(description = "Senha do usuário", example = "senha123")
    var senha: String? = null
}