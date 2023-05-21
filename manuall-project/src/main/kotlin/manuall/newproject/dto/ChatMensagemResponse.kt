package manuall.newproject.dto

import java.util.Date

data class ChatMensagemResponse (
    val selfSender: Boolean,
    val mensagem: String,
    val horario: Date,
    val anexo: String?
)