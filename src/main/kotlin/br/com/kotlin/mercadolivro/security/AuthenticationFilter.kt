package br.com.kotlin.mercadolivro.security

import br.com.kotlin.mercadolivro.controller.request.LoginRequest
import br.com.kotlin.mercadolivro.exception.AuthenticationException
import br.com.kotlin.mercadolivro.repository.CustomerRepository
import br.com.kotlin.mercadolivro.service.CustomerService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
    private val jwtUtil: JWTUtil

): UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
            val id = customerRepository.findByEmail(loginRequest.email)?.id
            val oauthToken = UsernamePasswordAuthenticationToken(id, loginRequest.password)

            return authenticationManager.authenticate(oauthToken)
        } catch (ex: Exception){
            throw AuthenticationException("Erro ao autenticar", "999")
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
       val id = (authResult.principal as UserCustomDetails).id 
       val token = jwtUtil.generateToken(id.toString())
       response.addHeader("Authorization", "Bearer $token")
    }
}