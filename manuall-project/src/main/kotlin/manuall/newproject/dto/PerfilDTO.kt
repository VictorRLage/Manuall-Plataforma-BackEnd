package manuall.newproject.dto

data class PerfilDTO(
    val nome: String,
    val orcamento_min: Double,
    val orcamento_max: Double,
    val presta_aula: Boolean,
    val estado: String,
    val cidade: String,
    val avaliacoes: List<AvaliacaoDTO>
)