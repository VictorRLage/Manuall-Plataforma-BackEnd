package manuall.newproject.service

import manuall.newproject.domain.FormOrcamento
import manuall.newproject.domain.Solicitacao
import manuall.newproject.dto.solicitacao.OrcamentoDto
import manuall.newproject.dto.solicitacao.SolicitacaoDto
import manuall.newproject.repository.*
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SolicitacaoService(
    val jwtTokenManager: JwtTokenManager,
    val solicitacaoRepository: SolicitacaoRepository,
    val usuarioServicoRepository: UsuarioServicoRepository,
    val usuarioRepository: UsuarioRepository,
    val servicoRepository: ServicoRepository,
    val solicitacaoImgRepository: SolicitacaoImgRepository,
    val chatRepository: ChatRepository,
    val formOrcamentoRepository: FormOrcamentoRepository
) {

    fun getServicosPrestadorPorPrestador(idPrestador: Int): ResponseEntity<List<Int>> {
        return ResponseEntity.status(200).body(usuarioServicoRepository.findServicosByUsuarioId(idPrestador))
    }

    fun enviarSolicitacao(token: String, solicitacaoDto: SolicitacaoDto): ResponseEntity<Void> {

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

        formOrcamento.mensagem = orcamentoDto.mensagem
        formOrcamento.orcamento = orcamentoDto.orcamento
        formOrcamentoRepository.save(formOrcamento)

        val solicitacao = solicitacaoRepository.findById(orcamentoDto.solicitacaoId).get()
        solicitacao.formOrcamento = formOrcamento
        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(201).build()
    }
}