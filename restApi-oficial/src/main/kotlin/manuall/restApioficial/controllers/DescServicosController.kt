package manuall.restApioficial.controllers

import manuall.restApioficial.models.DescServicos
import manuall.restApioficial.repositories.DescServicosRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/descservicos")
class DescServicosController
    (val DescServicosRepository: DescServicosRepository)
{
    @GetMapping("/{id}")
    fun buscarDescServicosPorId(@PathVariable id: Int): DescServicos? {
        return DescServicosRepository.findById(id).orElseThrow()
    }
}