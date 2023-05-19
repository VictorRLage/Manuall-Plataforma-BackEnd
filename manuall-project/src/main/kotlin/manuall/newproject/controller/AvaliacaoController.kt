package manuall.newproject.controller

import manuall.newproject.service.AvaliacaoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin("http://localhost:3000")
data class AvaliacaoController (
    val avaliacaoService: AvaliacaoService
) {
}