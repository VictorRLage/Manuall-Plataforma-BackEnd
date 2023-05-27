package manuall.newproject.dto.chat

data class ChatMensagensResponse (
    val destinatario: ChatPegarDadosDestinatarioDto,
    val mensagens: List<ChatMensagemResponse>
)