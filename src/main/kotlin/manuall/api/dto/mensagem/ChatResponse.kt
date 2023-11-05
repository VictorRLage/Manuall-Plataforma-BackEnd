package manuall.api.dto.mensagem

import java.util.Date

data class ChatResponse(
    val solicitacaoId: Int,
    val usuarioNome: String?,
    val usuarioPfp: String?,
    val mensagens: List<MensagemDto>,
    val dataInicio: Date?,
)