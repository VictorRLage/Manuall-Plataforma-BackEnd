package manuall.restApioficial.controllers

import manuall.restApioficial.models.Avaliacao
import manuall.restApioficial.repositories.AvaliacaoRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/avaliacao")
data class AvaliacaoController (
    val avaliacaoRepository: AvaliacaoRepository
) {

    @GetMapping("/{id}")
    fun buscarAvaliacaoPorId(@PathVariable id: Int): Avaliacao? {
        return avaliacaoRepository.findById(id).orElseThrow()
    }
}