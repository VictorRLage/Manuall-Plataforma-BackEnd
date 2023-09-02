package manuall.api.dto.chat

data class ChatMensagensResponse (
    val destinatario: ChatPegarDadosDestinatarioDto,
    val mensagens: List<ChatMensagemResponse>
)