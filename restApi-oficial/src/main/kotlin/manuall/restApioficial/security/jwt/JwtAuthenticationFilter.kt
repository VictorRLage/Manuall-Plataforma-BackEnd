package manuall.restApioficial.security.jwt

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter (
    val jwtTokenManager: JwtTokenManager
): OncePerRequestFilter() {
}