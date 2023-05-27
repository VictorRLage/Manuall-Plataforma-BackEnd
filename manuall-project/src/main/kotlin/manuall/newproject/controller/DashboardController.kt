package manuall.newproject.controller

import manuall.newproject.dto.DashboardComplitudeCadastroDto
import manuall.newproject.dto.PegarRegiaoDTO
import manuall.newproject.service.DashboardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("http://localhost:3000")
class DashboardController(
    val dashboardService: DashboardService
) {

    @GetMapping("/geral/canal/{tipoUsuario}")
    fun usuariosCanal(@PathVariable tipoUsuario:Int):List<String> {
        return dashboardService.usuariosCanal(tipoUsuario)
    }

    @GetMapping("/geral/regiao")
    fun pegarRegiao():List<PegarRegiaoDTO> {
        return dashboardService.pegarRegiao()
    }

    @GetMapping("/geral/complitudeCadastro")
    fun taxaComplitudeCadastro(): ResponseEntity<DashboardComplitudeCadastroDto> {
        return dashboardService.taxaComplitudeCadastro()
    }
}