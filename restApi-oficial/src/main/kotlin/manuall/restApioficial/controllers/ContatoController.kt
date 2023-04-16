package manuall.restApioficial.controllers

import manuall.restApioficial.models.Contato
import manuall.restApioficial.repositories.ContatoRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/contato")
class ContatoController
    (val contatoRepository: ContatoRepository)
{
    @GetMapping("/{id}")
    fun buscarContatoPorId(@PathVariable id: Int): Contato? {
        return contatoRepository.findById(id).orElseThrow()
    }
}