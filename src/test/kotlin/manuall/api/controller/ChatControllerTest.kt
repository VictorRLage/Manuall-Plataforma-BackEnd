package manuall.api.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class ChatControllerTest {

    @Test
    fun `classe deve estar anotada com @RestController`() {
        val classe = ChatController::class.java
        assertTrue(classe.isAnnotationPresent(org.springframework.web.bind.annotation.RestController::class.java))
    }
    @Test
    fun `classe deve estar anotada com @RequestMapping e ('chat')`() {
        val classe = ChatController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.RequestMapping::class.java)

        assertNotNull(annotation)
        assertEquals("/chat", annotation.value[0])
    }
    @Test
    fun `classe deve estar anotada com @CrossOrigin e se referenciando ao IP localhost na porta 5173`() {
        val classe = ChatController::class.java
        val annotation = classe.getAnnotation(org.springframework.web.bind.annotation.CrossOrigin::class.java)
        assertNotNull(annotation)
        assertEquals("http://localhost:5173", annotation.value[0])
    }

    @Test
    fun `método getChats() deve estar anotado com @GetMapping`() {
        val method = ChatController::getChats
        assertTrue(method.hasAnnotation<GetMapping>())
    }
    @Test
    fun `método getChats() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = ChatController::getChats
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[1] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun `método getMensagensByIdSolicitacao() deve estar anotado com @GetMapping e ('{idSolicitacao}')`() {
        val method = ChatController::getMensagensByIdSolicitacao
        assertTrue(method.hasAnnotation<GetMapping>())
        val annotation = method.findAnnotation<GetMapping>()
        assertEquals("/{idSolicitacao}", annotation!!.value[0])
    }
    @Test
    fun `método getMensagensByIdSolicitacao() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = ChatController::getMensagensByIdSolicitacao
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[1] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun `getChatsByIdSolicitacaoIdUltimaMensagem()`() {
        val method = ChatController::getChatsByIdSolicitacaoIdUltimaMensagem
        assertTrue(method.hasAnnotation<GetMapping>())
        val annotation = method.findAnnotation<GetMapping>()
        assertEquals("/{idSolicitacao}/buscar/{idUltimaMensagem}", annotation!!.value[0])
    }
    @Test
    fun `método getChatsByIdSolicitacaoIdUltimaMensagem() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = ChatController::getChatsByIdSolicitacaoIdUltimaMensagem
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[1] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

    @Test
    fun `mandarMensagem()`() {
        val method = ChatController::mandarMensagem
        assertTrue(method.hasAnnotation<PostMapping>())
    }
    @Test
    fun `método mandarMensagem() possui a anotação SecurityRequirement e name = 'Bearer'`() {
        val method = ChatController::mandarMensagem
        assertTrue(method.hasAnnotation<SecurityRequirement>())
        val annotation = method.annotations[1] as SecurityRequirement
        assertEquals("Bearer", annotation.name)
    }

}