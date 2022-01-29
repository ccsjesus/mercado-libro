package br.com.kotlin.mercadolivro.repository

import br.com.kotlin.mercadolivro.enums.BookStatus
import br.com.kotlin.mercadolivro.model.BookModel
import br.com.kotlin.mercadolivro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface BookRepository: JpaRepository<BookModel, Int> {

    fun findByStatus(status: BookStatus, page: Pageable): Page<BookModel>

    fun findByCustomer(customer: CustomerModel): List<BookModel>
}