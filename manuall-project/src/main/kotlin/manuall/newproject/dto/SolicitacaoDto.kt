package manuall.newproject.dto

data class SolicitacaoDto (
    val idPrestador: Int,
    val idServico: Int,
    val tamanho: Double,
    val medida: String,
    val descricao: String,
    val anexo: List<String>
)