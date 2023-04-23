package manuall.restApioficial.controllers

import manuall.restApioficial.models.AreaUsuario
import manuall.restApioficial.repositories.AreaUsuarioRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/areausuario")
class AreaUsuarioController (
        val areaUsuarioRepository: AreaUsuarioRepository
) {

    @GetMapping("/{id}")
    fun buscarAreaUsuarioPorId(@PathVariable id: Int): AreaUsuario? {
        return areaUsuarioRepository.findById(id).orElseThrow()
    }
}