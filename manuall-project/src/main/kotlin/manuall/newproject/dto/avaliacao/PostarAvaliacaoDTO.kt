package manuall.newproject.dto.avaliacao

data class PostarAvaliacaoDTO (
    val idSolicitacao: Int,
    var idContratante: Int,
    val nota: Int,
    val descricao: String
)