package manuall.api.dto.mensagem

data class ChatMensagemRequest(
    val token: String?,
    val solicitacaoId: Int,
    val mensagem: String?,
    val anexo: String?
)