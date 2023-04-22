//package manuall.restApioficial.security.jwt
//
//import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.security.Keys
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.stereotype.Component
//import java.util.*
//import java.util.stream.Collectors
//
//@Component
//class JwtProvider(
//    @Value("\${jwt.secret}") private val secretKey: String,
//    @Value("\${jwt.expiration}") private val expiration: Int
//) {
//
//    fun generateToken(authentication: Authentication): String {
//        val authorities: String = authentication.authorities.stream().map(GrantedAuthority::getAuthority)
//            .collect(Collectors.joining(","))
//
//        val validity = Date(Date().time + expiration * 1000)
//
//        return Jwts.builder().setSubject(authentication.name)
//            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray())).setIssuedAt(Date(System.currentTimeMillis()))
//            .setExpiration(validity).compact()
//    }
//}