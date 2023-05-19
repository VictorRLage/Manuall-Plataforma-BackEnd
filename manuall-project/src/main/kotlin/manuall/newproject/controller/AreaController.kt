package manuall.newproject.controller

import manuall.newproject.service.AreaService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/areas")
@CrossOrigin("http://localhost:3000")
class AreaController(
        val areaService: AreaService
) {
}