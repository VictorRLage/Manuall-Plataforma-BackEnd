package manuall.restApioficial.controllers

import manuall.restApioficial.models.Endereco
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/endereco")
class EnderecoController {

    @GetMapping("/{id}")
    fun buscarEnderecoPorId(@PathVariable id: Int): ResponseEntity<Endereco> {
        TODO()
    }

    @PostMapping
    fun cadastrarEndereco(@RequestBody endereco: Endereco): ResponseEntity<Endereco> {
        TODO()
    }
}