package manuall.newproject.controller

import manuall.newproject.service.ServicoService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/servicos")
class ServicoController (
    val servicoService: ServicoService
) {
}