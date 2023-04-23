package manuall.restApioficial.controllers

import manuall.restApioficial.models.DadosBancarios
import manuall.restApioficial.repositories.DadosBancariosRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dadosBancarios")
class DadosBancariosController(
        val dadosBancariosRepository: DadosBancariosRepository
) {

    @GetMapping("/{id}")
    fun buscarDadosBancariosPorId(@PathVariable id: Int): DadosBancarios? {
        return dadosBancariosRepository.findById(id).orElseThrow()
    }
}