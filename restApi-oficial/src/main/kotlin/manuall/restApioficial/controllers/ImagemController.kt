package manuall.restApioficial.controllers

import manuall.restApioficial.models.Imagem
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/imagem")
class ImagemController {

    @GetMapping("/{id}")
    fun pegarImagensPorId(@PathVariable id: Int): ResponseEntity<Imagem> {
        TODO()
    }

    @PostMapping
    fun cadastrarImagens(@RequestBody imagem: Imagem): ResponseEntity<Imagem> {
        TODO()
    }
}