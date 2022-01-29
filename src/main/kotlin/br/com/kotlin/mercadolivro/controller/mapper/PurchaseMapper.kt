package br.com.kotlin.mercadolivro.controller.mapper

import br.com.kotlin.mercadolivro.controller.request.PostPurchaseRequest
import br.com.kotlin.mercadolivro.model.PurchaseModel
import br.com.kotlin.mercadolivro.service.BookService
import br.com.kotlin.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService

) {
    fun toModel(postPurchaseRequest: PostPurchaseRequest) : PurchaseModel {
        val customer = customerService.getById(postPurchaseRequest.customerId)
        val books = bookService.findAllByIds(postPurchaseRequest.bookIds)

        return PurchaseModel(
            customer = customer,
            books = books.toMutableList(),
            price = books.sumOf { it.price }

        )

    }
}