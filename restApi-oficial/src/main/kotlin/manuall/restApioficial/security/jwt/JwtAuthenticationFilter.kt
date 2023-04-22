//package manuall.restApioficial.security.jwt
//
//import org.springframework.http.HttpHeaders
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.web.filter.OncePerRequestFilter
//import javax.servlet.FilterChain
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//class JwtAuthenticationFilter(
//    private val jwtProvider: JwtProvider,
//    private val userDetailsService: UserDetailsService
//): OncePerRequestFilter() {
//
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
//
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response)
//            return
//        }
//
////        val token = authorizationHeader.substring(7)
////        val username = jwtProvider.extractUsername(token)
////
////        if (username == null || SecurityContextHolder.getContext().authentication != null) {
////            filterChain.doFilter(request, response)
////            return
////        }
//
////        val userDetails = userDetailsService.loadUserByUsername(username)
////
////        if (!jwtProvider.validateToken(token, userDetails)) {
////            filterChain.doFilter(request, response)
////            return
////        }
//
////        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
////        SecurityContextHolder.getContext().authentication = authentication
////        filterChain.doFilter(request, response)
//    }
//}