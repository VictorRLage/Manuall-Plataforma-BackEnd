package manuall.newproject.controller

import manuall.newproject.service.UsuarioService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/areausuario")
class AreaUsuarioController (
        val areaUsuarioService: UsuarioService
) {
}