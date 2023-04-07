package manuall.restApioficial.controllers

import manuall.restApioficial.models.Imagem
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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