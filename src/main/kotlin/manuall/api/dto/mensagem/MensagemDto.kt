package manuall.api.dto.mensagem

import java.util.Date

open class MensagemDto(
    open val id: Int,
    open val visto: Boolean,
    open val selfSender: Boolean,
    open val horario: Date?,
    open val mensagem: String?,
    open val anexo: String?
)