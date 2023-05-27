package manuall.newproject.dto.usuario

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UsuarioLoginCheckRequest (

    @field:NotBlank
    @field:Email
    val email: String
)