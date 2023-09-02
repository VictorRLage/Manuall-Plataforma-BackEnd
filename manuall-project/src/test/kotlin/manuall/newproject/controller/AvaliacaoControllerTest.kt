package manuall.newproject.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.full.hasAnnotation

class AvaliacaoControllerTest {

    @Test
    fun `classe deve estar anotada com @RestController`() {
        val classe = AvaliacaoController::class.java
        assertTrue(classe.isAnnotationPresent(RestController::class.java))
    }
    @Test
    fun `classe deve estar anotada com @RequestMapping e ('avaliacao')`() {
        val classe = AvaliacaoController::class.java
        val annotation = classe.getAnnotation(RequestMapping::class.java)

        assertNotNull(annotation)
        assertEquals("/avaliacao", annotation.value[0])
    }
    @Test
    fun `classe deve estar anotada com @CrossOrigin e se referenciando ao IP localhost na porta 5173`() {
        val classe = AvaliacaoController::class.java
        val annotation = classe.getAnnotation(CrossOrigin::class.java)

        assertNotNull(annotation)
        assertEquals("http://localhost:5173", annotation.value[0])
    }

    @Test
    fun `verificar se o método postarAvaliacao() possui a anotação PostMapping`() {
        val method = AvaliacaoController::postarAvaliacao
        assertTrue(method.hasAnnotation<PostMapping>())
    }
    @Test
    fun `verificar se o método postarAvaliacao() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = AvaliacaoController::postarAvaliacao
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[0] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }
}