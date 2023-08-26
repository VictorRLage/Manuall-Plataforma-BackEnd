package manuall.newproject.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class SolicitacaoControllerTest {

    @Test
    fun `classe deve estar anotada com @RestController`() {
        val classe = SolicitacaoController::class.java
        assertTrue(classe.isAnnotationPresent(org.springframework.web.bind.annotation.RestController::class.java))
    }
    @Test
    fun `classe deve estar anotada com @RequestMapping e ('solicitacao')`() {
        val classe = SolicitacaoController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.RequestMapping::class.java)

        assertNotNull(annotation)
        assertEquals("/solicitacao", annotation.value[0])
    }
    @Test
    fun `classe deve estar anotada com @CrossOrigin e se referenciando ao IP localhost na porta 5173`() {
        val classe = SolicitacaoController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.CrossOrigin::class.java)

        assertNotNull(annotation)
        assertEquals("http://localhost:5173", annotation.value[0])
    }

    @Test
    fun getServicosPrestadorPorPrestador() {
        val method = SolicitacaoController::getServicosPrestadorPorPrestador
        assertTrue(method.hasAnnotation<GetMapping>())
        val annotation = method.findAnnotation<GetMapping>()
        assertEquals("/servicos/{idPrestador}", annotation!!.value[0])
    }

    @Test
    fun enviarSolicitacao() {
        val method = SolicitacaoController::enviarSolicitacao
        assertTrue(method.hasAnnotation<PostMapping>())
    }
    @Test
    fun `método enviarSolicitacao() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = SolicitacaoController::enviarSolicitacao
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[1] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun responderSolicitacao() {
        val method = SolicitacaoController::responderSolicitacao
        assertTrue(method.hasAnnotation<PostMapping>())
        val annotation = method.findAnnotation<PostMapping>()
        assertEquals("/{idSolicitacao}/{aceitar}", annotation!!.value[0])
    }
    @Test
    fun `método responderSolicitacao() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = SolicitacaoController::responderSolicitacao
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[1] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun cancelarSolicitacao() {
        val method = SolicitacaoController::cancelarSolicitacao
        assertTrue(method.hasAnnotation<PostMapping>())
        val annotation = method.findAnnotation<PostMapping>()
        assertEquals("/{idSolicitacao}/cancelar", annotation!!.value[0])
    }
    @Test
    fun `método cancelarSolicitacao() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = SolicitacaoController::cancelarSolicitacao
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[1] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun deletarSolicitacao() {
        val method = SolicitacaoController::deletarSolicitacao
        assertTrue(method.hasAnnotation<DeleteMapping>())
        val annotation = method.findAnnotation<DeleteMapping>()
        assertEquals("/{idSolicitacao}", annotation!!.value[0])
    }
    @Test
    fun `método deletarSolicitacao() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = SolicitacaoController::deletarSolicitacao
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[2] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun postarOrcamento() {
        val method = SolicitacaoController::postarOrcamento
        assertTrue(method.hasAnnotation<PostMapping>())
        val annotation = method.findAnnotation<PostMapping>()
        assertEquals("/postarOrcamento", annotation!!.value[0])
    }
    @Test
    fun `método postarOrcamento() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = SolicitacaoController::postarOrcamento
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[1] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }
}
