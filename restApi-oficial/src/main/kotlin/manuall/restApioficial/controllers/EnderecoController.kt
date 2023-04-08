package manuall.restApioficial.controllers

import manuall.restApioficial.models.Endereco
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
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