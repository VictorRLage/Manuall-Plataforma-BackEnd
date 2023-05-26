package manuall.newproject.service

import manuall.newproject.domain.Avaliacao
import manuall.newproject.domain.Usuario
import manuall.newproject.dto.PostarAvaliacaoDTO
import manuall.newproject.repository.AvaliacaoRepository
import manuall.newproject.repository.SolicitacaoRepository
import manuall.newproject.repository.UsuarioRepository
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AvaliacaoService(
    val jwtTokenManager: JwtTokenManager,
    val usuarioRepository: UsuarioRepository,
    val solicitacaoRepository: SolicitacaoRepository,
    val avaliacaoRepository: AvaliacaoRepository
) {

    fun postarAvaliacao(token: String, postarAvaliacaoDTO: PostarAvaliacaoDTO): ResponseEntity<Int> {
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        val avaliacao = Avaliacao()

        avaliacao.contratanteUsuario = usuarioRepository.findById(usuarioEncontrado.id).get()
        avaliacao.prestadorUsuario =
            solicitacaoRepository.findById(postarAvaliacaoDTO.idSolicitacao).get().prestadorUsuario
        avaliacao.descricao = postarAvaliacaoDTO.descricao
        avaliacao.nota = postarAvaliacaoDTO.nota

        avaliacaoRepository.save(avaliacao)

        return ResponseEntity.status(201).build()

    }
}