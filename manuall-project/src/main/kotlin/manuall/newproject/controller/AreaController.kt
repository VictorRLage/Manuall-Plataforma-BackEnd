package manuall.newproject.controller

import manuall.newproject.model.Area
import manuall.newproject.repository.AreaRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/areas")
@CrossOrigin("http://localhost:3000")
class AreaController(
        val areaRepository: AreaRepository
) {

    @GetMapping("/{id}")
    fun buscarAreaPorId(@PathVariable id: Int): Area? {
        return areaRepository.findById(id).orElseThrow()
    }
}