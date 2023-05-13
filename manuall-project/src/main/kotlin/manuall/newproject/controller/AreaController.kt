package manuall.newproject.controller

import manuall.newproject.service.AreaService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/areas")
class AreaController(
        val areaService: AreaService
) {
}