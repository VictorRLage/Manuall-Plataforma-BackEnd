package manuall.newproject.dto

import manuall.newproject.domain.Usuario

data class LoginResponse (
    val qntPerfis: Int,
    val usuario: List<Usuario>?
)