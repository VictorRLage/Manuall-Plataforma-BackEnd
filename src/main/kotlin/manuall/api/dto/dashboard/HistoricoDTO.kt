package manuall.api.dto.dashboard

import java.util.*

data class HistoricoDTO(
    val prestador_usuario_id: Long,
    val servico_id: Long,
    val form_orcamento_id: Long,
    val status: String,
    val descricao: String,
    val area_id: Long,
    val nome: String,
    var orcamento: Double
)