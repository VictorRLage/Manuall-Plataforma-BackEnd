package manuall.api.dto.dashboard

import java.util.*

data class HistoricoDTO(
    val status: Int?,
    val dataInicio: Date?,
    val dataFim: Date?,
    val prestadorNome: String?,
    val servico: String?,
    var valorOrcamento: Double?,
    val prestadorPfp: String?,
//    val mediaAvaliacoes: Double?,
//    val qntAvaliacoes: Long,
    val descricao: String?,
    val notaDada: Int?,
    val solicitacaoId: Int,
)