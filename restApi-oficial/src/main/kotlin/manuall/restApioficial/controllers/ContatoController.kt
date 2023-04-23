package manuall.restApioficial.controllers

import manuall.restApioficial.models.Contato
import manuall.restApioficial.repositories.ContatoRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contatos")
class ContatoController(
        val contatoRepository: ContatoRepository
) {

    @GetMapping("/{id}")
    fun buscarContatoPorId(@PathVariable id: Int): Contato? {
        return contatoRepository.findById(id).orElseThrow()
    }
}