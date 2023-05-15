package manuall.newproject.dto

import manuall.newproject.domain.Chat

data class ChatMensagensResponse (
    val destinatario: ChatPegarDadosDestinatarioDto,
    val mensagens: List<Chat>
)