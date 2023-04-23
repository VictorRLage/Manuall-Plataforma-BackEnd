package manuall.restApioficial.controllers

import manuall.restApioficial.models.Prospect
import manuall.restApioficial.repositories.ProspectRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/prospects")
data class ProspectController (
    val prospectRepository: ProspectRepository
) {

    @GetMapping("/checar-canal/{email}")
    fun checarCanal(@PathVariable email: String): ResponseEntity<Prospect?> {
        val prospect = prospectRepository.findByEmail(email)
        return if (prospect == null) {
            ResponseEntity.status(204).body(null)
        } else {
            ResponseEntity.status(200).body(prospect)
        }
    }
}
