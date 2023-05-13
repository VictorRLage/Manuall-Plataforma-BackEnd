package manuall.newproject.controller

import manuall.newproject.service.DescServicosService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/descServicos")
class DescServicosController (
        val descServicosService: DescServicosService
) {
}