package manuall.newproject.service

import manuall.newproject.domain.Servico
import manuall.newproject.domain.UsuarioServico
import manuall.newproject.repository.SolicitacaoRepository
import manuall.newproject.repository.UsuarioServicoRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SolicitacaoService (
    val solicitacaoRepository: SolicitacaoRepository,
    val usuarioServicoRepository: UsuarioServicoRepository
) {

    fun getServicosPrestadorPorPrestador(idPrestador: Int): ResponseEntity<List<Int>> {
        return ResponseEntity.status(200).body(usuarioServicoRepository.findServicosByUsuarioId(idPrestador))
    }
}