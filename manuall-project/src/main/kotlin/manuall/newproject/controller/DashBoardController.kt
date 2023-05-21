package manuall.newproject.controller

import manuall.newproject.domain.Usuario
import manuall.newproject.service.DashboardService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/dashboard")
@CrossOrigin("http://localhost:3000")
class DashBoardController(
    val dashboardService: DashboardService
) {

    // dashboard geral
    @GetMapping("/geral/canal/{tipoUsuario}")
    fun usuariosCanal(@PathVariable tipoUsuario:Int):List<String> {
        return dashboardService.usuariosCanal(tipoUsuario)
    }


    @GetMapping("/geral/regiao")
    fun pegarRegiao() {
        return dashboardService.pegarRegiao()
    }

}