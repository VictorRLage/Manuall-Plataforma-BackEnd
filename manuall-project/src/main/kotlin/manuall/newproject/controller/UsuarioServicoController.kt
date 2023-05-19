package manuall.newproject.controller

import manuall.newproject.service.UsuarioServicoService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/usuarioServicos")
@CrossOrigin("http://localhost:3000")
class UsuarioServicoController (
    val usuarioServicoService: UsuarioServicoService
) {
}