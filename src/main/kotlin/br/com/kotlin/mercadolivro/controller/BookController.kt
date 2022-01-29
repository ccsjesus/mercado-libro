package br.com.kotlin.mercadolivro.controller

import br.com.kotlin.mercadolivro.controller.request.PostBookRequest
import br.com.kotlin.mercadolivro.controller.request.PutBookRequest
import br.com.kotlin.mercadolivro.controller.response.BookResponse
import br.com.kotlin.mercadolivro.controller.response.PageResponse
import br.com.kotlin.mercadolivro.extension.toBookModel
import br.com.kotlin.mercadolivro.extension.toPageResponse
import br.com.kotlin.mercadolivro.extension.toResponse
import br.com.kotlin.mercadolivro.service.BookService
import br.com.kotlin.mercadolivro.service.CustomerService
import io.swagger.annotations.ApiModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("books")
class BookController(
    val bookService: BookService,
    val customerService: CustomerService

) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid request: PostBookRequest) {
        var customer = customerService.getById(request.customerId);

        bookService.create(request.toBookModel(customer));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@PageableDefault(page = 0, size = 0) page: Pageable): PageResponse<BookResponse> =
        bookService.getAll(page).map { it.toResponse() }.toPageResponse();

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    fun getBookActives(@PageableDefault(page = 0, size = 0) page: Pageable): Page<BookResponse> =
        bookService.findActives(page).map { it.toResponse() }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable id: Int): BookResponse =
        bookService.findById(id).toResponse()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeBook(@PathVariable id: Int) =
        bookService.delete(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateBook(@PathVariable id: Int, @RequestBody bookRequest: PutBookRequest) {
        val book = bookService.findById(id);
        bookService.update(bookRequest.toBookModel(book))
    }

}