package manuall.newproject.service

import manuall.newproject.domain.Prospect
import manuall.newproject.repository.ProspectRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProspectService (
    val prospectRepository: ProspectRepository
) {

    fun checarCanal(email: String): ResponseEntity<Prospect?> {

        val prospect = prospectRepository.findByEmail(email)
        return if (prospect == null) {
            ResponseEntity.status(204).body(null)
        } else {
            ResponseEntity.status(200).body(prospect)
        }

    }
}