package manuall.api.dto.solicitacao

data class PostarAvaliacaoDto (
    val solicitacaoId: Int,
    val nota: Int,
    val descricao: String
)