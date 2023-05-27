package manuall.newproject.service

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
    fun `testando se retorna o status 204 quando eu coloco um email inexistente`() {
        assertEquals(
            HttpStatus.valueOf(204),
            usuarioService.loginChecar("joaquim.pires@joaquim.pires").statusCode
        )
    }
}