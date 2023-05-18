package manuall.newproject.config

import manuall.newproject.repository.TokenBlacklistRepository
import manuall.newproject.repository.UsuarioRepository
import manuall.newproject.security.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.cors.CorsConfiguration
import java.util.*

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig (
    val jwtAuthenticationService: JwtAuthenticationService,
    val autenticacaoJwtEntryPoint: JwtAuthenticationEntryPoint,
    val usuarioRepository: UsuarioRepository,
    val tokenBlacklistRepository: TokenBlacklistRepository
) {

    companion object {
        private val URLS_PERMITIDAS = arrayOf(
            AntPathRequestMatcher("/swagger-ui/**"),
            AntPathRequestMatcher("/swagger-ui.html"),
            AntPathRequestMatcher("/swagger-resources"),
            AntPathRequestMatcher("/swagger-resources/**"),
            AntPathRequestMatcher("/v3/api-docs/**"),
            AntPathRequestMatcher("/usuarios/login"),
            AntPathRequestMatcher("/usuarios/cadastrar"),
            AntPathRequestMatcher("/usuarios/cadastrar/1"),
            AntPathRequestMatcher("/usuarios/cadastrar/2/contratante/{id}"),
            AntPathRequestMatcher("/usuarios/cadastrar/2/prestador/{id}"),
            AntPathRequestMatcher("/usuarios/cadastrar/3/prestador/areas"),
            AntPathRequestMatcher( "/usuarios/cadastrar/3/prestador/servicos/{id}"),
            AntPathRequestMatcher("/usuarios/cadastrar/3/prestador/{id}")
        )
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.headers()
            .frameOptions().disable()
            .and()
            .cors()
            .configurationSource { buildCorsConfiguration() }
            .and()
            .csrf()
            .disable()
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers(*URLS_PERMITIDAS)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .exceptionHandling()
            .authenticationEntryPoint(autenticacaoJwtEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.authenticationProvider(
            JwtAuthenticationProvider(
                jwtAuthenticationService,
                passwordEncoder()
            )
        )
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun jwtAuthenticationEntryPointBean(): JwtAuthenticationEntryPoint {
        return JwtAuthenticationEntryPoint()
    }

    @Bean
    fun jwtAuthenticationFilterBean(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwtAuthenticationService, jwtAuthenticationUtilBean())
    }

    @Bean
    fun jwtAuthenticationUtilBean(): JwtTokenManager {
        return JwtTokenManager(usuarioRepository, tokenBlacklistRepository)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun buildCorsConfiguration(): CorsConfiguration {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("*")
        configuration.allowedMethods = listOf(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
        )
        configuration.allowedHeaders =
            listOf(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION)
        return configuration
    }
}