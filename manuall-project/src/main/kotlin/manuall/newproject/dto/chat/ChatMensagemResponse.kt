package manuall.newproject.dto.chat

import java.util.Date

data class ChatMensagemResponse (
    val id: Int,
    val selfSender: Boolean,
    val mensagem: String,
    val horario: Date,
    val anexo: String?
)