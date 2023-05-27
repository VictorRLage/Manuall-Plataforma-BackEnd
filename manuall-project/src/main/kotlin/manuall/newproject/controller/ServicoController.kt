package manuall.newproject.controller

import manuall.newproject.service.ServicoService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/servico")
@CrossOrigin("http://localhost:3000")
class ServicoController (
    val servicoService: ServicoService
) {
}