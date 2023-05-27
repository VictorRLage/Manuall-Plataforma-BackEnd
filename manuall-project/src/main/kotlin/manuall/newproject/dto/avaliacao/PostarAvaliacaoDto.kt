package manuall.newproject.dto.avaliacao

data class PostarAvaliacaoDto (
    val idSolicitacao: Int,
    var idContratante: Int,
    val nota: Int,
    val descricao: String
)