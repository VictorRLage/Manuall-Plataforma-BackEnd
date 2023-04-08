package manuall.restApioficial.controllers

import manuall.restApioficial.models.Area
import manuall.restApioficial.repositories.AreaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/area")
class AreaController(val repository: AreaRepository) {

    @GetMapping("/{id}")
    fun buscarAreaPorId(@PathVariable id: Int): Area? {
        return repository.findById(id).orElseThrow()
    }
}