package manuall.newproject.dto

import java.util.*

data class ChatMensagemRequest (
    val idSolicitacao: Int,
    val mensagem: String,
    val horario: Date,
    val anexo: String?
)