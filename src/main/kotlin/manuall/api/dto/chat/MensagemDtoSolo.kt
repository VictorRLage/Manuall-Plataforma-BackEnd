package manuall.api.dto.chat

import java.util.*

class MensagemDtoSolo(
    val solicitacaoId: Int,
    override val selfSender: Boolean,
    override val horario: Date?,
    override val mensagem: String?,
    override val anexo: String?
): MensagemDto(
    selfSender = selfSender,
    horario = horario,
    mensagem = mensagem,
    anexo = anexo
)