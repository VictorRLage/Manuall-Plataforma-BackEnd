package manuall.newproject.controller

import manuall.newproject.service.DescServicosService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/descServicos")
@CrossOrigin("http://localhost:3000")
class DescServicosController (
        val descServicosService: DescServicosService
) {
}