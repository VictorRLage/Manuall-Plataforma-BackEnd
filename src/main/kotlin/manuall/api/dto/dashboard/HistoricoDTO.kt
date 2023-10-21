package manuall.api.dto.dashboard

import java.util.*

data class HistoricoDTO(
    val prestador_usuario_id: Int?,
    val servico_id: Int?,
    val form_orcamento_id: Int?,
    val status: Int?,
    val data_inicio: Date?,
    val data_fim: Date?,
    val descricao: String?,
    val area_id: Int?,
    val nome: String?,
    var orcamento: Double?,
)