package manuall.newproject.controller

import manuall.newproject.service.UsuarioServicoService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/usuarioServicos")
class UsuarioServicoController (
    val usuarioServicoService: UsuarioServicoService
) {
}