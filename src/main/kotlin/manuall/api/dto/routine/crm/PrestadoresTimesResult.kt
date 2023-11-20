package manuall.api.dto.routine.crm

import java.util.Date

data class PrestadoresTimesResult(
    val id: Int,
    val email: String,
    val lastSolicitacaoDate: Date?,
    val lastCrmDate: Date?,
)