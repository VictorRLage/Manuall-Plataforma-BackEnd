package manuall.api.dto.chat

data class ChatMensagemRequest(
    val token: String?,
    val solicitacaoId: Int,
    val mensagem: String?,
    val anexo: String?
)