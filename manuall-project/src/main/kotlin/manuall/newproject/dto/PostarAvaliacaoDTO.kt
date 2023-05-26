package manuall.newproject.dto

data class PostarAvaliacaoDTO (
    val idSolicitacao: Int,
    var idContratante: Int,
    val nota: Int,
    val descricao: String
)