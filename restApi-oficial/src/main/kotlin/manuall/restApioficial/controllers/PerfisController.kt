package manuall.restApioficial.controllers

import manuall.restApioficial.models.Perfis
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/perfis")
class PerfisController {

    @GetMapping
    fun verPerfis(): ResponseEntity<List<Perfis>> {
        TODO()
    }
}