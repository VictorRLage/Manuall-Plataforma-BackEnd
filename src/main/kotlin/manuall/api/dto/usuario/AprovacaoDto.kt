package manuall.api.dto.usuario

data class AprovacaoDto(
    val id: Int,
    val nome: String?,
    val email: String?,
    val telefone: String?,
    val cpf: String?,
    val cidade: String?,
    val estado: String?,
    val area: String?,
    val servicos: List<String>,
    val orcamentoMin: Double?,
    val orcamentoMax: Double?,
    val ensino: Boolean?,
    val statusProcesso: Int?,
    val status: Int?
)