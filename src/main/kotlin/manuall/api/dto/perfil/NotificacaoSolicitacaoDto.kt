package manuall.api.dto.perfil

data class NotificacaoSolicitacaoDto(
    val incluiAula: Boolean?,
    val servico: String?,
    val tamanho: Double?,
    val medida: String?,
    val descricao: String?,
    val imagens: List<String?>,
)