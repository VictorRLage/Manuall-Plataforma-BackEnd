package manuall.restApioficial.repositories

import manuall.restApioficial.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {

    fun findByEmail(email: String): List<Usuario?>

    fun findByEmailAndSenha(email: String, senha: String): List<Usuario>

    fun findByEmailAndTipoUsuario(email: String, tipoUsuario: Int): Optional<Usuario?>
}