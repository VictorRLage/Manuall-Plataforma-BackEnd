package manuall.api.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.GetMapping
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class DashboardControllerTest {

    @Test
    fun `classe deve estar anotada com @RestController`() {
        val classe = DashboardController::class.java
        assertTrue(classe.isAnnotationPresent(org.springframework.web.bind.annotation.RestController::class.java))
    }
    @Test
    fun `classe deve estar anotada com @RequestMapping e ('dashboard')`() {
        val classe = DashboardController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.RequestMapping::class.java)

        assertNotNull(annotation)
        assertEquals("/dashboard", annotation.value[0])
    }
    @Test
    fun `classe deve estar anotada com @CrossOrigin e se referenciando ao IP localhost na porta 5173`() {
        val classe = DashboardController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.CrossOrigin::class.java)

        assertNotNull(annotation)
        assertEquals("http://localhost:5173", annotation.value[0])
    }

//    @Test
//    fun `usuariosCanal()`() {
//        val method = DashboardController::usuariosCanal
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/geral/canal/{tipoUsuario}", annotation!!.value[0])
//    }
//
//    @Test
//    fun `pegarRegiao()`() {
//        val method = DashboardController::pegarRegiao
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/geral/regiao", annotation!!.value[0])
//    }
//
//    @Test
//    fun taxaComplitudeCadastro() {
//        val method = DashboardController::taxaComplitudeCadastro
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/geral/complitudeCadastro", annotation!!.value[0])
//    }
}