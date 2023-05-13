package manuall.newproject.controller

import manuall.newproject.service.DadosEnderecoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dadosEnderecos")
class DadosEnderecoController (
        val dadosEnderecoService: DadosEnderecoService
) {
}