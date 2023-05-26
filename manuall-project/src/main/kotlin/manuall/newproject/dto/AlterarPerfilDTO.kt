package manuall.newproject.dto

import manuall.newproject.domain.Area

data class AlterarPerfilDTO(
    val area: Area,
    val descricao: String,
    val profile: String,
    val nome: String,
    val orcamento_min: Double,
    val orcamento_max: Double,
    val presta_aula: Boolean,
    val estado: String,
    val cidade: String,
    val imagens: List<String>,
    val servicos: List<String>
)