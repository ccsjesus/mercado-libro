package br.com.kotlin.mercadolivro.security

import br.com.kotlin.mercadolivro.controller.response.ErrorResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntrypoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
         response.contentType = "application/json"
         response.status = HttpServletResponse.SC_UNAUTHORIZED
         val errorResponse =  ErrorResponse(HttpStatus.UNAUTHORIZED .value(), "Unauthorizade", "000", null)
        response.outputStream.print(jacksonObjectMapper().writeValueAsString(errorResponse))
    }
}