package manuall.api.dto.usuario

data class PrestadorCardDto(
    val id: Int?,
    val nome: String?,
    val anexoPfp: String?,
    val area: Int?,
    val orcamentoMin: Double?,
    val orcamentoMax: Double?,
    val prestaAula: Boolean?,
    val cidade: String?,
    val mediaAvaliacoes: Double,
)