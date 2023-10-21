package manuall.api.dto.dashboard

data class SolicitacoesMensaisDto(
    val ano: Int,
    val mes: Int,
    val qntTotal: Long,
    val qntRealizadas: Long,
)