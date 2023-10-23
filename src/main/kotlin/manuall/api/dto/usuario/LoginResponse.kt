package manuall.api.dto.usuario

data class LoginResponse (
    val token: String?,
    val msg: String?,
    val tipoUsuario: Int?,
    val fase: Int?,
    val usuarioId: Int?,
    val plano: Int?
)