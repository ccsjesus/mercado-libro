package br.com.kotlin.mercadolivro.service

import br.com.kotlin.mercadolivro.exception.AuthenticationException
import br.com.kotlin.mercadolivro.repository.CustomerRepository
import br.com.kotlin.mercadolivro.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailCustomService(
    private val customerRepository: CustomerRepository
): UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
       var customer = customerRepository.findById(id.toInt()).orElseThrow{AuthenticationException("User not found", "999")}
       return UserCustomDetails(customer)
    }
}