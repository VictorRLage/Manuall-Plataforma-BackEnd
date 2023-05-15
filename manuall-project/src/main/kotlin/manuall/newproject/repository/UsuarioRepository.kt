package manuall.newproject.repository

import manuall.newproject.domain.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {

    fun findByEmail(
        email: String?
    ): List<Optional<Usuario>>

    fun findByEmailAndSenha(
        email: String?,
        senha: String?
    ): Optional<Usuario>

    fun findByEmailAndTipoUsuario(
        email: String?,
        tipoUsuario: Int?
    ): Optional<Usuario>

    fun findByTipoUsuarioAndPlano(

    )


}
