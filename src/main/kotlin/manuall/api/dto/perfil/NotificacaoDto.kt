package manuall.api.dto.perfil

import java.util.Date

data class NotificacaoDto(
    val solicitacaoId: Int,
    val nomeUsuario: String,
    val type: Int,
    // 1: Pendente
    // 2: Aceita
    // 3: Recusada
    // 4: Finalizada - Apenas para prestador
    val date: Date?,
    val solicitacao: NotificacaoSolicitacaoDto?,
)