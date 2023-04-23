package manuall.restApioficial.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

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

    @Override
    protected fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .anyRequest().permitAll()
            .and().csrf().disable()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .build()
    }
}