package manuall.api.dto.chat

import java.util.Date

open class MensagemDto(
    open val selfSender: Boolean,
    open val horario: Date?,
    open val mensagem: String?,
    open val anexo: String?
)