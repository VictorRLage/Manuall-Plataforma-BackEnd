package manuall.api.dto.avaliacao

data class PostarAvaliacaoDto (
    val solicitacaoId: Int,
    val nota: Int,
    val descricao: String
)