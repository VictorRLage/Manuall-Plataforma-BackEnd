package manuall.api.controller.routine

import manuall.api.dto.routine.crm.UsuarioCrm
import manuall.api.service.routine.CrmRoutineService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/routine/crm")
class CrmRoutineController(
    val crmRoutineService: CrmRoutineService
) {

    @Value("\${routine.secret}")
    private val secret: String? = null

    @GetMapping("/ociosos")
    fun getPrestadoresOciosos(
        @RequestHeader("RoutineAuth") routineAuth: String?,
    ): ResponseEntity<List<UsuarioCrm>> {
        if (routineAuth != secret) return ResponseEntity.status(404).build()
        return crmRoutineService.getPrestadoresOciosos()
    }

    @GetMapping("/heavy")
    fun getHeavyPrestadores(
        @RequestHeader("RoutineAuth") routineAuth: String?,
    ): ResponseEntity<List<UsuarioCrm>> {
        if (routineAuth != secret) return ResponseEntity.status(404).build()
        return crmRoutineService.getHeavyPrestadores()
    }

    @GetMapping("/recentes")
    fun getContratantesRecentes(
        @RequestHeader("RoutineAuth") routineAuth: String?,
    ): ResponseEntity<List<Any>> {
        if (routineAuth != secret) return ResponseEntity.status(404).build()
        return crmRoutineService.getContratantesRecentes()
    }

    @PostMapping("/{tipo}/iniciarCrm/{usuarioId}")
    fun iniciarCrmOciosos(
        @RequestHeader("RoutineAuth") routineAuth: String?,
        @PathVariable tipo: String,
        @PathVariable usuarioId: Int,
    ): ResponseEntity<Unit> {
        if (routineAuth != secret) return ResponseEntity.status(404).build()
        return crmRoutineService.iniciarCrm(tipo, usuarioId)
    }
}