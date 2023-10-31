package manuall.api.dto.mensagem

import java.util.*

class MensagemDtoSolo(
    val solicitacaoId: Int,
    override val id: Int,
    override val visto: Boolean,
    override val selfSender: Boolean,
    override val horario: Date?,
    override val mensagem: String?,
    override val anexo: String?
): MensagemDto(
    id = id,
    visto = visto,
    selfSender = selfSender,
    horario = horario,
    mensagem = mensagem,
    anexo = anexo
)