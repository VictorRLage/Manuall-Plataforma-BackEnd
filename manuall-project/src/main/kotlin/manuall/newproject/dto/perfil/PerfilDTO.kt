package manuall.newproject.dto.perfil

import manuall.newproject.dto.avaliacao.AvaliacaoDto
import manuall.newproject.dto.avaliacao.NotificacaoDto

data class PerfilDTO(
    val area: String,
    val descricao: String,
    val profile: String,
    val nome: String,
    val orcamentoMin: Double,
    val orcamentoMax: Double,
    val prestaAula: Boolean,
    val estado: String,
    val cidade: String,
    val imagens: List<String>,
    val servicos: List<String>,
    val avaliacoes: List<AvaliacaoDto>,
    val notificacoes: List<NotificacaoDto>
)