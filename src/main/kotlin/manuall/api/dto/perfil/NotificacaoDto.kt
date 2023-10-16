package manuall.api.dto.perfil

import java.sql.Date

data class NotificacaoDto(
    val solicitacaoId: Int,
    val nomeUsuario: String,
    val type: String,
    val date: Date?,
    val isSolicitacao: NotificacaoSolicitacaoDto?,
)