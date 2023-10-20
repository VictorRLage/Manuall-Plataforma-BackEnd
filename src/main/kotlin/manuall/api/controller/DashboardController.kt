package manuall.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import manuall.api.dto.dashboard.DashboardDto
import manuall.api.service.DashboardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("http://localhost:5173")
class DashboardController(
    val dashboardService: DashboardService
) {

    @GetMapping("/{from}/{to}")
    @SecurityRequirement(name = "Bearer")
    fun getDashboard(
        @RequestHeader("Authorization") @Schema(hidden = true) token: String?,
        @PathVariable @Schema(defaultValue = "2017-10-19T15:06:18.209753954") from: LocalDateTime,
        @PathVariable @Schema(defaultValue = "2024-10-19T15:06:18.209753954") to: LocalDateTime,
    ): ResponseEntity<DashboardDto> {
        return dashboardService.getDashboard(
            token,
            Date.from(from.atZone(ZoneId.systemDefault()).toInstant()),
            Date.from(to.atZone(ZoneId.systemDefault()).toInstant()),
        )
    }
}