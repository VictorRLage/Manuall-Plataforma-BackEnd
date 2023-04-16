package manuall.restApioficial.controllers

import manuall.restApioficial.models.DadosBancarios
import manuall.restApioficial.repositories.DadosBancariosRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/dadosbancarios")
class DadosBancariosController
    (val dadosBancariosRepository: DadosBancariosRepository)
{
    @GetMapping("/{id}")
    fun buscarDadosBancariosPorId(@PathVariable id: Int): DadosBancarios? {
        return dadosBancariosRepository.findById(id).orElseThrow()
    }
}