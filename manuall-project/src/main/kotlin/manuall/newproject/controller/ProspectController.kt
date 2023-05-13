package manuall.newproject.controller

import manuall.newproject.domain.Prospect
import manuall.newproject.service.ProspectService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/prospects")
data class ProspectController (
    val prospectService: ProspectService
) {

    @GetMapping("/checar-canal/{email}")
    fun checarCanal(@PathVariable email: String): ResponseEntity<Prospect?> {
        return prospectService.checarCanal(email)
    }
}
