package manuall.restApioficial.controllers

import manuall.restApioficial.models.Area
import manuall.restApioficial.repositories.AreaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/areas")
class AreaController(
        val areaRepository: AreaRepository
) {

    @GetMapping("/{id}")
    fun buscarAreaPorId(@PathVariable id: Int): Area? {
        return areaRepository.findById(id).orElseThrow()
    }

    @PostMapping
    fun post():String {
        return "a"
    }
}