package manuall.api.controller.routine

import manuall.api.service.routine.PipefyRoutineService
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/routine/pipefy")
class PipefyRoutineController(
    val pipefyRoutineService: PipefyRoutineService
) {

    @Value("\${routine.secret}")
    private val secret: String? = null


}