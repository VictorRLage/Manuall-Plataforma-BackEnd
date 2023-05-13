package manuall.newproject.controller

import manuall.newproject.service.SolicitacaoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/solicitacoes")
data class SolicitacaoController (
    val solicitacaoService: SolicitacaoService
) {
}