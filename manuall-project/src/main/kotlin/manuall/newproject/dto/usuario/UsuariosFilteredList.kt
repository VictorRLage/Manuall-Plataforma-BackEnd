package manuall.newproject.dto.usuario

data class UsuariosFilteredList (
    val id: Int,
    val nome: String,
    val anexoPfp: String?,
    val idArea: Int?,
    val orcamentoMin: Double?,
    val orcamentoMax: Double?,
    val cidade: String?,
    val prestaAula: Boolean?,
    val mediaAvaliacoes: Double?,
    val qntAvaliacoes: Long
)
