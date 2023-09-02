package manuall.api.dto.perfil

data class AlterSenhaRequest (
    val senhaAntiga: String,
    val senhaNova: String
)