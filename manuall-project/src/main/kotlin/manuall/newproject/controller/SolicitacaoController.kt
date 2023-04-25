package manuall.newproject.controller

import manuall.newproject.model.Solicitacao
import manuall.newproject.repository.SolicitacaoRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/solicitacoes")
@CrossOrigin("http://localhost:3000")
data class SolicitacaoController (
    val solicitacaoRepository: SolicitacaoRepository
) {

    @GetMapping("/{id}")
    fun buscarSolicitacaoPorId(@PathVariable id: Int): Solicitacao? {
        return solicitacaoRepository.findById(id).orElseThrow()
    }
}