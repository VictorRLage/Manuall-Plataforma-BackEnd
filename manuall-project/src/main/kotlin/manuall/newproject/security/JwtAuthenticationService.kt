package manuall.newproject.security

import manuall.newproject.domain.Administrador
import manuall.newproject.domain.Contratante
import manuall.newproject.domain.Prestador
import manuall.newproject.domain.Usuario
import manuall.newproject.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtAuthenticationService (
    val usuarioRepository: UsuarioRepository
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val classeUsuario = when (username.substring(0,1)) {
            "1" -> Contratante::class.java
            "2" -> Prestador::class.java
            "3" -> Administrador::class.java
            else -> Contratante::class.java
        }
        val usuarioOpt: Optional<Usuario> = usuarioRepository.findByEmailAndTipoUsuario(username.substring(1), classeUsuario)
        if (usuarioOpt.isEmpty) {
            throw UsernameNotFoundException(String.format("usuario: %s nao encontrado", username))
        }
        return UserDetailsDto(usuarioOpt.get())
    }
}