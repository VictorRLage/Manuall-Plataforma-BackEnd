package manuall.api.service

import manuall.api.domain.*
import manuall.api.dto.solicitacao.PostarAvaliacaoDto
import manuall.api.dto.solicitacao.OrcamentoDto
import manuall.api.dto.solicitacao.SolicitacaoDto
import manuall.api.repository.*
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.Date

@Service
class SolicitacaoService(
    val jwtTokenManager: JwtTokenManager,
    val solicitacaoRepository: SolicitacaoRepository,
    val usuarioServicoRepository: UsuarioServicoRepository,
    val usuarioRepository: UsuarioRepository,
    val servicoRepository: ServicoRepository,
    val solicitacaoImgRepository: SolicitacaoImgRepository,
    val chatRepository: ChatRepository,
    val formOrcamentoRepository: FormOrcamentoRepository,
    val avaliacaoRepository: AvaliacaoRepository
) {

    fun getServicosPrestadorPorPrestador(idPrestador: Int): ResponseEntity<List<Int>> {
        return ResponseEntity.status(200).body(usuarioServicoRepository.findServicosByUsuarioId(idPrestador))
    }

    fun enviarSolicitacao(token: String, solicitacaoDto: SolicitacaoDto): ResponseEntity<Void> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        usuarioEncontrado as Contratante

        val solicitacao = Solicitacao()
        solicitacao.contratanteUsuario = usuarioEncontrado
        solicitacao.prestadorUsuario = usuarioRepository.findById(solicitacaoDto.idPrestador).get() as Prestador
        solicitacao.tamanho = solicitacaoDto.tamanho
        solicitacao.medida = solicitacaoDto.medida
        solicitacao.descricao = solicitacaoDto.descricao
        solicitacao.status = 1
        solicitacao.incluiAula = solicitacaoDto.incluiAula
        solicitacao.servico = servicoRepository.findById(solicitacaoDto.idServico).get()
        solicitacao.dataInicio = Date()

        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(201).build()
    }

    fun responderSolicitacao(token: String, idSolicitacao: Int, aceitar: Boolean): ResponseEntity<Void> {

        if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        val solicitacao = solicitacaoRepository.findById(idSolicitacao).get()

        solicitacao.status = if (aceitar) 2 else 4

        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(200).build()
    }

    fun cancelarSolicitacao(token: String, idSolicitacao: Int): ResponseEntity<Void> {

        if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        val solicitacao = solicitacaoRepository.findById(idSolicitacao).get()

        solicitacao.status = 3

        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(200).build()
    }

    fun deletarSolicitacao(token: String, idSolicitacao: Int): ResponseEntity<Void> {

        if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        solicitacaoImgRepository.deleteBySolicitacaoId(idSolicitacao)
        chatRepository.deleteBySolicitacaoId(idSolicitacao)
        solicitacaoRepository.deleteById(idSolicitacao)

        return ResponseEntity.status(200).build()
    }

    fun enviarOrcamento(token: String, orcamentoDto: OrcamentoDto): ResponseEntity<Int> {
        if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        val formOrcamento = FormOrcamento()
        val solicitacao = solicitacaoRepository.findById(orcamentoDto.solicitacaoId).get()

        formOrcamento.mensagem = orcamentoDto.mensagem
        formOrcamento.orcamento = orcamentoDto.orcamento
        formOrcamentoRepository.save(formOrcamento)

        solicitacao.formOrcamento = formOrcamento
        if (solicitacao.dataFim == null) solicitacao.dataFim = Date()

        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(201).build()
    }

    fun enviarAvaliacao(token: String, postarAvaliacaoDTO: PostarAvaliacaoDto): ResponseEntity<Int> {

        jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        val avaliacao = Avaliacao()

        avaliacao.descricao = postarAvaliacaoDTO.descricao
        avaliacao.nota = postarAvaliacaoDTO.nota
        avaliacaoRepository.save(avaliacao)

        val solicitacao = solicitacaoRepository.findById(postarAvaliacaoDTO.solicitacaoId).get()
        solicitacao.avaliacao = avaliacao
        if (solicitacao.dataFim == null) {
            solicitacao.dataFim = java.util.Date()
        }
        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(201).build()

    }
}