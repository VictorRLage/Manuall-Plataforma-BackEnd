package manuall.api.service

import manuall.api.dto.dashboard.HistoricoDTO
import manuall.api.repository.HistoricoRepository
import manuall.api.security.JwtTokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class HistoricoService(
    val jwtTokenManager: JwtTokenManager,
    val historicoRepository: HistoricoRepository,
) {

    fun buscarHistorico(token: String?): ResponseEntity<List<HistoricoDTO>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(historicoRepository.buscarHistorico(usuario.id))
    }
}
