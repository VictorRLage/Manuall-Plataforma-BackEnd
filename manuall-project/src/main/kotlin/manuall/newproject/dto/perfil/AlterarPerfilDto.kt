package manuall.newproject.dto.perfil

import manuall.newproject.domain.Area

data class AlterarPerfilDto(
    val area: Area,
    val descricao: String,
    val profile: String,
    val nome: String,
    val orcamentoMin: Double,
    val orcamentoMax: Double,
    val prestaAula: Boolean,
    val estado: String,
    val cidade: String,
    val imagens: List<String>,
    val servicos: List<String>
)