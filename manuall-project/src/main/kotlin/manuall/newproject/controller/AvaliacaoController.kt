package manuall.newproject.controller

import manuall.newproject.service.AvaliacaoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliacoes")
data class AvaliacaoController (
    val avaliacaoService: AvaliacaoService
) {
}