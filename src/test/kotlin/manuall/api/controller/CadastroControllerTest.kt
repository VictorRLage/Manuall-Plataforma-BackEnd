package manuall.api.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class CadastroControllerTest {

    @Test
    fun `classe deve estar anotada com @RestController`() {
        val classe = CadastroController::class.java
        assertTrue(classe.isAnnotationPresent(RestController::class.java))
    }
    @Test
    fun `classe deve estar anotada com @RequestMapping e ('cadastrar')`() {
        val classe = CadastroController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.RequestMapping::class.java)

        assertNotNull(annotation)
        assertEquals("/cadastrar", annotation.value[0])
    }
    @Test
    fun `classe deve estar anotada com @CrossOrigin e se referenciando ao IP localhost na porta 5173`() {
        val classe = CadastroController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.CrossOrigin::class.java)

        assertNotNull(annotation)
        assertEquals("http://localhost:5173", annotation.value[0])
    }

    @Test
    fun `método checarProspect() deve estar anotado com @PostMapping e ('prospect')`() {
        val method = CadastroController::checarProspect
        assertTrue(method.hasAnnotation<PostMapping>())
        val annotation = method.annotations[0] as PostMapping
        assertEquals("/prospect", annotation.value[0])
    }

    @Test
    fun `metodo cadastrar1() deve estar anotado com @PostMapping e ('1')`() {
        val method = CadastroController::cadastrar1
        assertTrue(method.hasAnnotation<PostMapping>())
        val annotation = method.annotations[0] as PostMapping
        assertEquals("/1", annotation.value[0])
    }

    @Test
    fun `método cadastrar2() deve estar anotado com @PutMapping e ('2')`() {
        val method = CadastroController::cadastrar2
        assertTrue(method.hasAnnotation<PutMapping>())
        val annotation = method.annotations[0] as PutMapping
        assertEquals("/2/{id}", annotation.value[0])
    }

    @Test
    fun `método cadastrar3Prest() deve estar anotado com @PutMapping e ('3')`() {
        val method = CadastroController::cadastrar3
        assertTrue(method.hasAnnotation<PutMapping>())
        val annotation = method.annotations[0] as PutMapping
        assertEquals("/3/{id}", annotation.value[0])
    }

    @Test
    fun `método cadastrar4Prest() deve estar anotado com @PutMapping e ('4''{idPlano}')`() {
        val method = CadastroController::cadastrar4
        assertTrue(method.hasAnnotation<PutMapping>())
        val annotation = method.findAnnotation<PutMapping>()
        assertEquals("/4/{idPlano}", annotation!!.value[0])
    }
    @Test
    fun `verificar se o método cadastrarPrest4() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = CadastroController::cadastrar4
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[0] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }
}