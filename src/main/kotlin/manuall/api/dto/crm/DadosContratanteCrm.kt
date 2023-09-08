package manuall.api.dto.crm

import manuall.api.domain.Area
import java.util.Date

data class DadosContratanteCrm(
    val nome: String,
    val areas: List<Area>,
    val ultimaDataContratado: Date,
    val plano: Int
)