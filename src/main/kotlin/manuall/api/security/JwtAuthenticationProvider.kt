package manuall.api.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder

class JwtAuthenticationProvider (
    private val usuarioAutorizacaoService: JwtAuthenticationService,
    private val passwordEncoder: PasswordEncoder
): AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {

        val userDetails = usuarioAutorizacaoService.loadUserByUsername(authentication.name)

        return if (
            passwordEncoder.matches(
                authentication.credentials.toString(),
                userDetails.password
            )
        )
            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        else throw BadCredentialsException("Usuário ou senha inválidos")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}