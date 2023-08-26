package manuall.newproject.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.*
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class PerfilControllerTest {

    @Test
    fun `classe deve estar anotada com @RestController`() {
        val classe = PerfilController::class.java
        assertTrue(classe.isAnnotationPresent(org.springframework.web.bind.annotation.RestController::class.java))
    }
    @Test
    fun `classe deve estar anotada com @RequestMapping e ('perfil')`() {
        val classe = PerfilController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.RequestMapping::class.java)

        assertNotNull(annotation)
        assertEquals("/perfil", annotation.value[0])
    }
    @Test
    fun `classe deve estar anotada com @CrossOrigin e se referenciando ao IP localhost na porta 5173`() {
        val classe = PerfilController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.CrossOrigin::class.java)

        assertNotNull(annotation)
        assertEquals("http://localhost:5173", annotation.value[0])
    }

    @Test
    fun `acessarPerfilPrestador()`() {
        val method = PerfilController::acessarPerfilPrestador
        assertTrue(method.hasAnnotation<PostMapping>())
        val annotation = method.findAnnotation<PostMapping>()
        assertEquals("/acessar/{idPrestador}", annotation!!.value[0])
    }

    @Test
    fun `alterarPerfil`() {
        val method = PerfilController::alterarPerfil
        assertTrue(method.hasAnnotation<PutMapping>())
        val annotation = method.findAnnotation<PutMapping>()
        assertEquals("/alterar", annotation!!.value[0])
    }
    @Test
    fun `método alterarPerfil() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = PerfilController::alterarPerfil
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[0] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun checarPrestador() {
        val method = PerfilController::checarPrestador
        assertTrue(method.hasAnnotation<GetMapping>())
    }
    @Test
    fun `método checarPrestador() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = PerfilController::checarPrestador
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[0] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun atualizarSenha() {
        val method = PerfilController::atualizarSenha
        assertTrue(method.hasAnnotation<PatchMapping>())
        val annotation = method.findAnnotation<PatchMapping>()
        assertEquals("/alterar/senha", annotation!!.value[0])
    }
    @Test
    fun `método atualizarSenha() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = PerfilController::atualizarSenha
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[0] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun atualizarDesc() {
        val method = PerfilController::atualizarDesc
        assertTrue(method.hasAnnotation<PatchMapping>())
        val annotation = method.findAnnotation<PatchMapping>()
        assertEquals("/alterar/desc", annotation!!.value[0])
    }
    @Test
    fun `método atualizarDesc() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = PerfilController::atualizarDesc
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[0] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun deletar() {
        val method = PerfilController::deletar
        assertTrue(method.hasAnnotation<DeleteMapping>())
    }
    @Test
    fun `método deletar() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = PerfilController::deletar
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[1] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun atualizarFotoPerfil() {
        val method = PerfilController::atualizarFotoPerfil
        assertTrue(method.hasAnnotation<PatchMapping>())
        val annotation = method.findAnnotation<PatchMapping>()
        assertEquals("/alterar/fotoPerfil", annotation!!.value[0])
    }
    @Test
    fun `método atualizarFotoPerfil() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = PerfilController::atualizarFotoPerfil
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[0] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun getSolicitacoes() {
        val method = PerfilController::getSolicitacoes
        assertTrue(method.hasAnnotation<GetMapping>())
        val annotation = method.findAnnotation<GetMapping>()
        assertEquals("/solicitacoes", annotation!!.value[0])
    }
    @Test
    fun `método getSolicitacoes() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = PerfilController::getSolicitacoes
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[0] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun postarUrl() {
        val method = PerfilController::postarUrl
        assertTrue(method.hasAnnotation<PostMapping>())
        val annotation = method.findAnnotation<PostMapping>()
        assertEquals("/postarUrl", annotation!!.value[0])
    }
    @Test
    fun `método postarUrl() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = PerfilController::postarUrl
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[0] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }
}
