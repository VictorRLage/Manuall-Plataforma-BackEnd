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
    val mensagemRepository: MensagemRepository,
    val formOrcamentoRepository: FormOrcamentoRepository,
    val avaliacaoRepository: AvaliacaoRepository
) {

    fun buscarTodos(token: String?): ResponseEntity<List<Solicitacao>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(solicitacaoRepository.findAll())
    }

    fun getServicosPrestadorPorPrestador(idPrestador: Int): ResponseEntity<List<Int>> {
        return ResponseEntity.status(200).body(usuarioServicoRepository.findServicosIdByUsuarioId(idPrestador))
    }

    fun enviarSolicitacao(token: String?, solicitacaoDto: SolicitacaoDto): ResponseEntity<Unit> {

        val usuario = (
                jwtTokenManager.validateToken(token)
                    ?: return ResponseEntity.status(480).build()
                ) as Contratante

        val solicitacao = Solicitacao()
        solicitacao.contratante = usuario
        solicitacao.prestador = usuarioRepository.findById(solicitacaoDto.idPrestador).get() as Prestador
        solicitacao.tamanho = solicitacaoDto.tamanho
        solicitacao.medida = solicitacaoDto.medida
        solicitacao.descricao = solicitacaoDto.descricao
        solicitacao.status = 1
        solicitacao.incluiAula = solicitacaoDto.incluiAula
        solicitacao.servico = servicoRepository.findById(solicitacaoDto.idServico).get()
        solicitacao.dataInicio = Date()

        solicitacaoRepository.save(solicitacao)

        solicitacaoDto.anexo.forEach {
            val solicitacaoImg = SolicitacaoImg()
            solicitacaoImg.solicitacao = solicitacao
            solicitacaoImg.anexo = it
            solicitacaoImgRepository.save(solicitacaoImg)
        }

        return ResponseEntity.status(201).build()
    }

    fun responderSolicitacao(token: String?, idSolicitacao: Int, aceitar: Boolean): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        val solicitacao = solicitacaoRepository.findById(idSolicitacao).get()

        solicitacao.dataInicio = Date()
        solicitacao.status = if (aceitar) {
            2
        } else {
            solicitacao.dataFim = Date()
            4
        }

        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(200).build()
    }

    fun cancelarSolicitacao(token: String?, idSolicitacao: Int): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        val solicitacao = solicitacaoRepository.findById(idSolicitacao).get()

        solicitacao.status = 3

        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(200).build()
    }

    fun deletarSolicitacao(token: String?, idSolicitacao: Int): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        solicitacaoImgRepository.deleteBySolicitacaoId(idSolicitacao)
        mensagemRepository.deleteBySolicitacaoId(idSolicitacao)
        solicitacaoRepository.deleteById(idSolicitacao)

        return ResponseEntity.status(200).build()
    }

    fun enviarOrcamento(token: String?, orcamentoDto: OrcamentoDto): ResponseEntity<Int> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        val solicitacaoPossivel = solicitacaoRepository.findById(orcamentoDto.solicitacaoId)

        if (solicitacaoPossivel.isEmpty)
            return ResponseEntity.status(404).build()

        val solicitacao = solicitacaoPossivel.get()

        val formOrcamento = FormOrcamento()

        formOrcamento.mensagem = orcamentoDto.mensagem
        formOrcamento.orcamento = orcamentoDto.orcamento
        formOrcamento.solicitacao = solicitacao
        formOrcamentoRepository.save(formOrcamento)

        solicitacao.formOrcamento = formOrcamento
        if (solicitacao.dataFim == null) solicitacao.dataFim = Date()

        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(201).build()
    }

    fun enviarAvaliacao(token: String?, postarAvaliacaoDTO: PostarAvaliacaoDto): ResponseEntity<Int> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        val avaliacao = Avaliacao()
        val solicitacao = solicitacaoRepository.findById(postarAvaliacaoDTO.solicitacaoId).get()

        avaliacao.solicitacao = solicitacao
        avaliacao.descricao = postarAvaliacaoDTO.descricao
        avaliacao.nota = postarAvaliacaoDTO.nota
        avaliacaoRepository.save(avaliacao)

        solicitacao.avaliacao = avaliacao
        if (solicitacao.dataFim == null)
            solicitacao.dataFim = Date()
        solicitacaoRepository.save(solicitacao)

        return ResponseEntity.status(201).build()

    }
}