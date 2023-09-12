package manuall.api.dto.perfil

import manuall.api.dto.solicitacao.AvaliacaoDto
import manuall.api.dto.solicitacao.NotificacaoDto
import manuall.api.service.PerfilServicoServiceDto

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
    val imagens: List<String>,
    val servicos: List<PerfilServicoServiceDto>,
    val avaliacoes: List<AvaliacaoDto>,
    val notificacoes: List<NotificacaoDto>
)