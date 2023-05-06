package manuall.newproject.controller

import manuall.newproject.domain.Avaliacao
import manuall.newproject.repository.AvaliacaoRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliacoes")
data class AvaliacaoController (
    val avaliacaoRepository: AvaliacaoRepository
) {

    @GetMapping("/{id}")
    fun buscarAvaliacaoPorId(@PathVariable id: Int): Avaliacao? {
        return avaliacaoRepository.findById(id).orElseThrow()
    }
}