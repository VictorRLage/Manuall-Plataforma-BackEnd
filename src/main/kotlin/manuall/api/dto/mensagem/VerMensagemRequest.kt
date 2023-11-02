package manuall.api.dto.mensagem

data class VerMensagemRequest(
    val token: String?,
    val mensagemId: Int,
)