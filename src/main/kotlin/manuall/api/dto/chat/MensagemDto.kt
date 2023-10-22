package manuall.api.dto.chat

import java.util.Date

data class MensagemDto(
    val selfSender: Boolean,
    val horario: Date?,
    val mensagem: String?,
    val anexo: String?
)