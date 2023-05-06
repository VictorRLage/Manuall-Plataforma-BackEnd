package manuall.newproject.security

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
        println("username")
        println(username)
        val usuarioOpt: Optional<Usuario> = usuarioRepository.findByEmailAndTipoUsuario(username.substring(1), username.substring(0,1).toInt())
        if (usuarioOpt.isEmpty) {
            throw UsernameNotFoundException(String.format("usuario: %s nao encontrado", username))
        }
        return UserDetailsDto(usuarioOpt.get())
    }
}