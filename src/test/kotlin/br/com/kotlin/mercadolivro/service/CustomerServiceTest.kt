package br.com.kotlin.mercadolivro.service

import br.com.kotlin.mercadolivro.enums.CustomerStatus
import br.com.kotlin.mercadolivro.enums.Role
import br.com.kotlin.mercadolivro.model.CustomerModel
import br.com.kotlin.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

     @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var customerService: CustomerService


    @Test
    fun `should return all customers` () {

        val builderCustomers = listOf(buildCustomers(), buildCustomers())

        every { customerRepository.findAll() } returns builderCustomers
        every { customerRepository.findByNameContaining(any()) } returns builderCustomers


        val customers =  customerService.getAll(null)

        assertEquals(builderCustomers, customers)
        verify(exactly = 1) {customerRepository.findAll()}
        verify(atLeast = 1) {customerRepository.findByNameContaining(any())}

    }

    fun buildCustomers(
        id: Int? = null,
        name: String = "cezar",
        email: String = "${UUID.randomUUID()}@email.com",
        password: String = "password"
    ) = CustomerModel(
        id = id,
        name = name,
        email = email,
        status = CustomerStatus.ATIVO,
        password = password,
        roles = setOf(Role.CUSTOMER)
    )
}