package manuall.newproject.service

import manuall.newproject.domain.Avaliacao
import manuall.newproject.dto.avaliacao.PostarAvaliacaoDto
import manuall.newproject.repository.AvaliacaoRepository
import manuall.newproject.repository.SolicitacaoRepository
import manuall.newproject.repository.UsuarioRepository
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AvaliacaoService(
    val jwtTokenManager: JwtTokenManager,
    val usuarioRepository: UsuarioRepository,
    val solicitacaoRepository: SolicitacaoRepository,
    val avaliacaoRepository: AvaliacaoRepository
) {

    fun postarAvaliacao(token: String, postarAvaliacaoDTO: PostarAvaliacaoDto): ResponseEntity<Int> {

        jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        val avaliacao = Avaliacao()

        avaliacao.descricao = postarAvaliacaoDTO.descricao
        avaliacao.nota = postarAvaliacaoDTO.nota
        avaliacaoRepository.save(avaliacao)

        val solicitacao = solicitacaoRepository.findById(postarAvaliacaoDTO.solicitacaoId).get()
        solicitacao.avaliacao = avaliacao
        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(201).build()

    }
}