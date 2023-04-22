package manuall.restApioficial.controllers

import manuall.restApioficial.models.Solicitacao
import manuall.restApioficial.repositories.SolicitacaoRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/solicitacoes")
data class SolicitacaoController (
    val solicitacaoRepository: SolicitacaoRepository
) {

    @GetMapping("/{id}")
    fun buscarSolicitacaoPorId(@PathVariable id: Int): Solicitacao? {
        return solicitacaoRepository.findById(id).orElseThrow()
    }
}