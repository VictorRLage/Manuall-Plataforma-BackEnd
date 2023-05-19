package manuall.newproject.controller

import manuall.newproject.service.SolicitacaoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/solicitacoes")
@CrossOrigin("http://localhost:3000")
data class SolicitacaoController (
    val solicitacaoService: SolicitacaoService
) {
}