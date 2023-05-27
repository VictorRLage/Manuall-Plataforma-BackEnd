package manuall.newproject.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.RestController

class UsuarioControllerTest {

    @Test
    fun `classe deve estar anotada com @RestController`() {
        val classe = UsuarioController::class.java
        assertTrue(classe.isAnnotationPresent(RestController::class.java))
    }
}