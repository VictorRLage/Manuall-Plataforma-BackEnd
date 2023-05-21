package manuall.newproject.dto

data class ChatMensagensResponse (
    val destinatario: ChatPegarDadosDestinatarioDto,
    val mensagens: List<ChatMensagemResponse>
)