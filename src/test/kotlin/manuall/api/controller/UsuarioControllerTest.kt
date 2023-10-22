package manuall.api.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.*
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class UsuarioControllerTest {

//    @Test
//    fun `classe deve estar anotada com @RestController`() {
//        val classe = UsuarioController::class.java
//        assertTrue(classe.isAnnotationPresent(RestController::class.java))
//    }
//    @Test
//    fun `classe deve estar anotada com @RequestMapping e ('usuario')`() {
//        val classe = UsuarioController::class.java
//        val annotation = classe.getAnnotation(RequestMapping::class.java)
//
//        assertNotNull(annotation)
//        assertEquals("/usuario", annotation.value[0])
//    }
//    @Test
//    fun `classe deve estar anotada com @CrossOrigin e se referenciando ao IP localhost na porta 5173`() {
//        val classe = UsuarioController::class.java
//        val annotation = classe.getAnnotation(CrossOrigin::class.java)
//
//        assertNotNull(annotation)
//        assertEquals("http://localhost:5173", annotation.value[0])
//    }
//
//    @Test
//    fun getPrestadoresOrderByPlano() {
//        val method = UsuarioController::getPrestadoresOrderByPlano
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/prestadores", annotation!!.value[0])
//    }
//
//    @Test
//    fun getPrestadoresByAreaIdOrderByPlano() {
//        val method = UsuarioController::getPrestadoresByAreaIdOrderByPlano
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/prestadores/{idArea}", annotation!!.value[0])
//    }
//
//    @Test
//    fun getPrestadoresFiltrado() {
//        val method = UsuarioController::getPrestadoresFiltrados
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("prestadores/{idArea}/{filtro}/{crescente}", annotation!!.value[0])
//    }
//
//    @Test
//    fun buscarArea() {
//        val method = UsuarioController::buscarArea
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/areas", annotation!!.value[0])
//    }
//
//    @Test
//    fun buscarTiposServico() {
//        val method = UsuarioController::buscarTiposServico
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/servico/{idServico}", annotation!!.value[0])
//    }
//
//    @Test
//    fun loginChecar() {
//        val method = UsuarioController::loginChecar
//        assertTrue(method.hasAnnotation<PostMapping>())
//        val annotation = method.findAnnotation<PostMapping>()
//        assertEquals("/login/checar", annotation!!.value[0])
//    }
//
//    @Test
//    fun loginEfetuar() {
//        val method = UsuarioController::loginEfetuar
//        assertTrue(method.hasAnnotation<PostMapping>())
//        val annotation = method.findAnnotation<PostMapping>()
//        assertEquals("/login/efetuar", annotation!!.value[0])
//    }
//
//    @Test
//    fun logoff() {
//        val method = UsuarioController::logoff
//        assertTrue(method.hasAnnotation<PostMapping>())
//        val annotation = method.findAnnotation<PostMapping>()
//        assertEquals("/logoff", annotation!!.value[0])
//    }
//    @Test
//    fun `método logoff() possui a anotação SecurityRequirement e name = 'Bearer'`() {
//        val method = UsuarioController::logoff
//        assertTrue(method.hasAnnotation<SecurityRequirement>())
//        val annotation = method.annotations[0] as SecurityRequirement
//        assertEquals("Bearer", annotation.name)
//    }
//
//    @Test
//    fun checarValidadeLogin() {
//        val method = UsuarioController::checarValidadeLogin
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/login/checar/validade", annotation!!.value[0])
//    }
//    @Test
//    fun `método checarValidadeLogin() possui a anotação SecurityRequirement e name = 'Bearer'`() {
//        val method = UsuarioController::checarValidadeLogin
//        assertTrue(method.hasAnnotation<SecurityRequirement>())
//        val annotation = method.annotations[0] as SecurityRequirement
//        assertEquals("Bearer", annotation.name)
//    }
//
//    @Test
//    fun aprovacoesPendentes() {
//        val method = UsuarioController::aprovacoesPendentes
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/aprovacoesPendentes", annotation!!.value[0])
//    }
//    @Test
//    fun `método aprovacoesPendentes() possui a anotação SecurityRequirement e name = 'Bearer'`() {
//        val method = UsuarioController::aprovacoesPendentes
//        assertTrue(method.hasAnnotation<SecurityRequirement>())
//        val annotation = method.annotations[0] as SecurityRequirement
//        assertEquals("Bearer", annotation.name)
//    }
//
//    @Test
//    fun aprovar() {
//        val method = UsuarioController::aprovar
//        assertTrue(method.hasAnnotation<GetMapping>())
//        val annotation = method.findAnnotation<GetMapping>()
//        assertEquals("/aprovacoesPendentes/{idPrestador}/{aprovar}", annotation!!.value[0])
//    }
//    @Test
//    fun `método aprovar() possui a anotação SecurityRequirement e name = 'Bearer'`() {
//        val method = UsuarioController::aprovar
//        assertTrue(method.hasAnnotation<SecurityRequirement>())
//        val annotation = method.annotations[0] as SecurityRequirement
//        assertEquals("Bearer", annotation.name)
//    }
}