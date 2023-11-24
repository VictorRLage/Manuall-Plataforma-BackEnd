package manuall.api.controller.routine

import manuall.api.dto.routine.pipefy.MapeamentoProspect
import manuall.api.service.routine.PipefyRoutineService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/routine/pipefy")
class PipefyRoutineController(
    val pipefyRoutineService: PipefyRoutineService
) {

    @Value("\${routine.secret}")
    private val secret: String? = null

    @PostMapping
    fun postarProspects(
        @RequestHeader("RoutineAuth") routineAuth: String?,
        @RequestBody mapeamentoProspects: List<MapeamentoProspect>,
    ): ResponseEntity<Unit> {
        if (routineAuth != secret) return ResponseEntity.status(404).build()
        return pipefyRoutineService.postarProspects(mapeamentoProspects)
    }
}