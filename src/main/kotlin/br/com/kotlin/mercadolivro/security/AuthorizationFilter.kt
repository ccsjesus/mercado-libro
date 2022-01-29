package br.com.kotlin.mercadolivro.security

import br.com.kotlin.mercadolivro.exception.AuthenticationException
import br.com.kotlin.mercadolivro.repository.CustomerRepository
import br.com.kotlin.mercadolivro.service.UserDetailCustomService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val userCustomDetails: UserDetailCustomService,
    private val jwtUtil: JWTUtil
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorization = request.getHeader("Authorization")

        if(authorization != null && authorization.startsWith("Bearer")) {
            val auth = getAuthentication(authorization.split(" ")[1])
            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        if(!jwtUtil.isValidToken(token)) {
            throw AuthenticationException("Token invalido", "999")
        }
        val subject = jwtUtil.getSubject(token)
        val customer = userCustomDetails.loadUserByUsername(subject)
        return UsernamePasswordAuthenticationToken(customer, null, customer.authorities)
    }
}