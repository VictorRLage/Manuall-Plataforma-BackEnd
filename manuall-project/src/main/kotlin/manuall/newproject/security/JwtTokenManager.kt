package manuall.newproject.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import manuall.newproject.domain.Usuario
import manuall.newproject.repository.TokenBlacklistRepository
import manuall.newproject.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import javax.crypto.SecretKey

class JwtTokenManager (
    private val usuarioRepository: UsuarioRepository,
    private val tokenBlacklistRepository: TokenBlacklistRepository
) {

    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.validity}")
    private val jwtTokenValidity: Long = 0

    fun getUserFromToken(token: String): Usuario {
        val decriptacaoToken = getUsernameFromToken(token.substring(7))
        return usuarioRepository.findByEmailAndTipoUsuario(decriptacaoToken.substring(1), decriptacaoToken.substring(0,1).toInt()).get()
    }

    fun validarToken(token: String): Boolean {
        return tokenBlacklistRepository.findByToken(token).isEmpty
    }

    fun expirarToken(token: String) {
        tokenBlacklistRepository.delete(tokenBlacklistRepository.findByToken(token).get())
    }

    fun getUsernameFromToken(token: String): String {
        return getClaimForToken(token) { obj: Claims -> obj.subject }
    }

    private fun getExpirationDateFromToken(token: String?): Date {
        return getClaimForToken(
            token
        ) { obj: Claims -> obj.expiration }
    }

    fun generateToken(authentication: Authentication): String {

        authentication.authorities.stream().map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        return Jwts.builder().setSubject(authentication.name)
            .signWith(parseSecret()).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + jwtTokenValidity * 1000)).compact()
    }

    private fun <T> getClaimForToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String?): Boolean {
        val expirationDate = getExpirationDateFromToken(token)
        return expirationDate.before(Date(System.currentTimeMillis()))
    }

    private fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(parseSecret())
            .build()
            .parseClaimsJws(token).body
    }

    private fun parseSecret(): SecretKey {
        return Keys.hmacShaKeyFor(secret!!.toByteArray(StandardCharsets.UTF_8))
    }
}