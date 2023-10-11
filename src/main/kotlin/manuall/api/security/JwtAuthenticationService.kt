package manuall.api.security

import manuall.api.enums.TipoUsuario
import manuall.api.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Component
class JwtAuthenticationService (
    val usuarioRepository: UsuarioRepository
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        return UserDetailsDto(
            usuarioRepository.findByEmailAndTipoUsuario(
                username.substring(1),
                TipoUsuario.fromStringToClass(username.substring(0,1))
            ).orElseThrow { UsernameNotFoundException("Usuário $username não encontrado") }
        )
    }
}