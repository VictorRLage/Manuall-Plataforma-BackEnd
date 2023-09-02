package manuall.api.service

import manuall.api.dto.usuario.UsuarioLoginRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@SpringBootTest
class UsuarioServiceTest (
    @Autowired
    val usuarioService: UsuarioService
) {

    @Test
    fun `retornos loginChecar`() {
        assertEquals(
            HttpStatus.valueOf(204),
            usuarioService.loginChecar("joaquim.pires@joaquim.pires").statusCode
        )
        assertEquals(
            HttpStatus.valueOf(200),
            usuarioService.loginChecar("joaquim.pires@sptech.school").statusCode
        )
    }

    @Test
    fun loginEfetuar() {
        val usuario = UsuarioLoginRequest()
        usuario.email = "joaquim.pires@sptech.school"
        usuario.senha = "senha123"
        usuario.tipoUsuario = 1
        assertEquals(
            HttpStatus.valueOf(200),
            usuarioService.loginEfetuar(usuario).statusCode
        )
    }
}