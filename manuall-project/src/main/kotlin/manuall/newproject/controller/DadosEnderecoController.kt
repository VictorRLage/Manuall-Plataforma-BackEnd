package manuall.newproject.controller

import manuall.newproject.service.DadosEnderecoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dadosEnderecos")
@CrossOrigin("http://localhost:3000")
class DadosEnderecoController (
        val dadosEnderecoService: DadosEnderecoService
) {
}