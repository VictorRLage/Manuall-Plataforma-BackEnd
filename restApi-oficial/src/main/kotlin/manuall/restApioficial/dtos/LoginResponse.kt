package manuall.restApioficial.dtos

import manuall.restApioficial.models.Usuario

data class LoginResponse (
    val qntPerfis: Int,
    val usuario: List<Usuario>?
)