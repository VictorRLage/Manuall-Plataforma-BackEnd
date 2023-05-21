package manuall.newproject.service

import manuall.newproject.domain.Servico
import manuall.newproject.domain.Solicitacao
import manuall.newproject.domain.UsuarioServico
import manuall.newproject.dto.SolicitacaoDto
import manuall.newproject.repository.ServicoRepository
import manuall.newproject.repository.SolicitacaoRepository
import manuall.newproject.repository.UsuarioRepository
import manuall.newproject.repository.UsuarioServicoRepository
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SolicitacaoService (
    val jwtTokenManager: JwtTokenManager,
    val solicitacaoRepository: SolicitacaoRepository,
    val usuarioServicoRepository: UsuarioServicoRepository,
    val usuarioRepository: UsuarioRepository,
    val servicoRepository: ServicoRepository
) {

    fun getServicosPrestadorPorPrestador(idPrestador: Int): ResponseEntity<List<Int>> {
        return ResponseEntity.status(200).body(usuarioServicoRepository.findServicosByUsuarioId(idPrestador))
    }

    fun enviarSolicitacao(token: String, solicitacaoDto: SolicitacaoDto): ResponseEntity<Void> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        val solicitacao = Solicitacao()
        solicitacao.contratanteUsuario = usuarioEncontrado
        solicitacao.prestadorUsuario = usuarioRepository.findById(solicitacaoDto.idPrestador).get()
        solicitacao.tamanho = solicitacaoDto.tamanho
        solicitacao.medida = solicitacaoDto.medida
        solicitacao.descricao = solicitacaoDto.descricao
        solicitacao.status = 1
        solicitacao.servico = servicoRepository.findById(solicitacaoDto.idServico).get()

        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(201).build()
    }
}