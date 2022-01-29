package br.com.kotlin.mercadolivro.extension

import br.com.kotlin.mercadolivro.controller.request.PostBookRequest
import br.com.kotlin.mercadolivro.controller.request.PostCustomerRequest
import br.com.kotlin.mercadolivro.controller.request.PutBookRequest
import br.com.kotlin.mercadolivro.controller.request.PutCustomerRequest
import br.com.kotlin.mercadolivro.controller.response.BookResponse
import br.com.kotlin.mercadolivro.controller.response.CustomerResponse
import br.com.kotlin.mercadolivro.controller.response.PageResponse
import br.com.kotlin.mercadolivro.enums.BookStatus
import br.com.kotlin.mercadolivro.enums.CustomerStatus
import br.com.kotlin.mercadolivro.model.BookModel
import br.com.kotlin.mercadolivro.model.CustomerModel
import org.springframework.data.domain.Page

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email, status = CustomerStatus.ATIVO, password = this.password)
}

fun CustomerModel.toResponse(): CustomerResponse {
    return CustomerResponse(id = this.id, name = this.name, email = this.email, status = this.status)
}

fun PutCustomerRequest.toCustomerModel(customer: CustomerModel): CustomerModel {
    return CustomerModel(
        id = customer.id,
        name = this.name ?: customer.name,
        email = this.email ?: customer.email,
        status = CustomerStatus.ATIVO,
        password = customer.password)
}

fun PostBookRequest.toBookModel(customer : CustomerModel): BookModel {
    return BookModel(name = this.name, price = this.price, status = BookStatus.ATIVO, customer = customer)
}

fun PutBookRequest.toBookModel(book : BookModel): BookModel {
    return BookModel(
        id = book.id,
        name = this.name ?: book.name,
        price = this.price ?: book.price,
        status = book.status,
        customer = book.customer)
}

fun BookModel.toResponse(): BookResponse {
    return BookResponse(id = this.id,
    name = this.name,
    price = this.price,
    customer = this.customer,
    status = this.status)
}

fun <T> Page<T>.toPageResponse(): PageResponse<T> = PageResponse(
    this.content,
    this.number,
    this.totalElements,
    this.totalPages
)