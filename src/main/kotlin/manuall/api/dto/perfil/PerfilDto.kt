package manuall.api.dto.perfil

data class PerfilDto(
    val area: String?,
    val descricao: String?,
    val pfp: String?,
    val nome: String?,
    val orcamentoMin: Double?,
    val orcamentoMax: Double?,
    val prestaAula: Boolean?,
    val estado: String?,
    val cidade: String?,
    val imagens: List<PerfilImagemDto>,
    val servicos: List<PerfilServicoDto>,
    val avaliacoes: List<PerfilAvaliacaoDto>,
)