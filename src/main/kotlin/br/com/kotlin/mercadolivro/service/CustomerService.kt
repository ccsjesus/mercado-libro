package br.com.kotlin.mercadolivro.service

import br.com.kotlin.mercadolivro.enums.CustomerStatus
import br.com.kotlin.mercadolivro.enums.Errors
import br.com.kotlin.mercadolivro.enums.Role
import br.com.kotlin.mercadolivro.exception.NotFoundException
import br.com.kotlin.mercadolivro.model.CustomerModel
import br.com.kotlin.mercadolivro.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService (
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
)  {

    fun getAll(name: String? ): List<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it);
        }
        return customerRepository.findAll().toList();

    }

    fun create(customer: CustomerModel) {

        val customerCopy = customer.copy(
            roles = setOf(Role.CUSTOMER ),
            password = bCrypt.encode(customer.password)
        )

        customerRepository.save(customerCopy)
    }

    fun getCustomer(id: Int): CustomerModel{
        return customerRepository.findById(id).orElseThrow()
    }

    fun getById(id: Int): CustomerModel{
        return customerRepository.findById(id).orElseThrow{ NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code)}
    }

    fun updateCustomer(customer: CustomerModel) {

        if(!customerRepository.existsById(customer.id!!)){
            throw Exception()
        }

        customerRepository.save(customer);

    }

    fun deleteCustomer(customer: CustomerModel){
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO

        customerRepository.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email);
    }

    fun findByEmail(email: String): CustomerModel? {
        return customerRepository.findByEmail(email)
    }
}