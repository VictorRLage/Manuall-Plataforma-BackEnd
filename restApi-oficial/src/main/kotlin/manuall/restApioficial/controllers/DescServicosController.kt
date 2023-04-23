package manuall.restApioficial.controllers

import manuall.restApioficial.models.DescServicos
import manuall.restApioficial.repositories.DescServicosRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/descServicos")
class DescServicosController(
        val descServicosRepository: DescServicosRepository
) {

    @GetMapping("/{id}")
    fun buscarDescServicosPorId(@PathVariable id: Int): DescServicos? {
        return descServicosRepository.findById(id).orElseThrow()
    }
}