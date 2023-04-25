package manuall.newproject.controller

import manuall.newproject.model.DadosBancarios
import manuall.newproject.repository.DadosBancariosRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dadosBancarios")
@CrossOrigin("http://localhost:3000")
class DadosBancariosController (
        val dadosBancariosRepository: DadosBancariosRepository
) {

    @GetMapping("/{id}")
    fun buscarDadosBancariosPorId(@PathVariable id: Int): DadosBancarios? {
        return dadosBancariosRepository.findById(id).orElseThrow()
    }
}