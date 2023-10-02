package manuall.api.service

import manuall.api.dto.dashboard.HistoricoDTO
import manuall.api.repository.HistoricoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HistoricoService(@Autowired private val historicoRepository: HistoricoRepository) {

    fun buscarHistorico(usuario_id: Long): List<HistoricoDTO> {
        return historicoRepository.buscarHistorico(usuario_id)
    }
}
