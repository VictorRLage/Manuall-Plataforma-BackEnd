package manuall.newproject.controller

import manuall.newproject.service.TipoServicoService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/tiposServicos")
class TipoServicoController (
    val tipoServicoService: TipoServicoService
) {
}