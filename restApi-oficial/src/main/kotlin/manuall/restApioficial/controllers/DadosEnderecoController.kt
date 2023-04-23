package manuall.restApioficial.controllers

import manuall.restApioficial.models.DadosEndereco
import manuall.restApioficial.repositories.DadosEnderecoRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dadosEnderecos")
class DadosEnderecoController(
        val dadosEnderecoRepository: DadosEnderecoRepository
) {

    @GetMapping("/{id}")
    fun buscarDadosEnderecoPorId(@PathVariable id: Int): DadosEndereco? {
        return dadosEnderecoRepository.findById(id).orElseThrow()
    }
}