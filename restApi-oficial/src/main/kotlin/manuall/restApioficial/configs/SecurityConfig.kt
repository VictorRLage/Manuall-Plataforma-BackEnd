//package manuall.restApioficial.configs
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.http.HttpMethod
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.config.Customizer
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//class SecurityConfiguration {
//
//    private val URLS_PERMITIDAS: () -> AntPathRequestMatcher = {
//        AntPathRequestMatcher("/usuarios/**")
//        AntPathRequestMatcher("/swagger-ui/**")
//    }
//
////    @Bean
////    fun filterChain(http: HttpSecurity): SecurityFilterChain {
////        http.headers()
////            .frameOptions().disable()
////            .and()
////            .cors()
////            .configurationSource{request -> buildCorsConfiguration()}
////            .and()
////            .csrf()
////            .disable()
////            .authorizeHttpRequests {authorize ->
////                authorize.anyRequest()
////                .authenticated()
////            }
////            .exceptionHandling()
////            .authenticationEntryPoint(autenticacaoJwtEntryPoint)
////            .and()
////            .sessionManagement()
////            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
////        return http.build()
////    }
//
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http.authorizeHttpRequests {authz ->
//            authz.antMatchers(HttpMethod.DELETE, "/**").permitAll()
//            authz.antMatchers(HttpMethod.POST, "/**").permitAll()
//            authz.antMatchers(HttpMethod.PATCH, "/**").permitAll()
//            authz.antMatchers(HttpMethod.PUT, "/**").permitAll()
//            authz.antMatchers(HttpMethod.GET, "/**").permitAll()
//            authz.antMatchers(HttpMethod.DELETE, "/**").authenticated()
//            authz.antMatchers(HttpMethod.POST, "/**").authenticated()
//            authz.antMatchers(HttpMethod.PATCH, "/**").authenticated()
//            authz.antMatchers(HttpMethod.PUT, "/**").authenticated()
//            authz.antMatchers(HttpMethod.GET, "/**").authenticated()
//            authz.anyRequest().authenticated()
//        }.httpBasic(Customizer.withDefaults())
//
//        return http.build()
//    }
//
//    @Override
//    protected fun configure(http: HttpSecurity) {
//        http.authorizeRequests()
//            .anyRequest().permitAll()
//            .and().csrf().disable()
//    }
//
//    @Bean
//    @Throws(Exception::class)
//    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
//        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
//            .build()
//    }
//}