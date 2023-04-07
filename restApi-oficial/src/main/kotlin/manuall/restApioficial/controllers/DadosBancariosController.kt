package manuall.restApioficial.controllers

import manuall.restApioficial.models.DadosBancarios
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dadosBancarios")
class DadosBancariosController {

    @GetMapping("/{id}")
    fun buscarDadosBancariosPorId(@PathVariable id: Int): ResponseEntity<DadosBancarios> {
        TODO()
    }

    @PostMapping
    fun cadastrarDadosBancarios(@RequestBody dadosBancarios: DadosBancarios): ResponseEntity<DadosBancarios> {
        TODO()
    }
}