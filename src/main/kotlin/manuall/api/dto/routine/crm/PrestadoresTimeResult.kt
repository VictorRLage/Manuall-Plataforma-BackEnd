package manuall.api.dto.routine.crm

import java.util.*

data class PrestadoresTimeResult(
    val id: Int,
    val email: String,
    val lastCrmDate: Date?,
)