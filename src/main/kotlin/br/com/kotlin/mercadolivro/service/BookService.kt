package br.com.kotlin.mercadolivro.service

import br.com.kotlin.mercadolivro.enums.BookStatus
import br.com.kotlin.mercadolivro.enums.Errors
import br.com.kotlin.mercadolivro.exception.NotFoundException
import br.com.kotlin.mercadolivro.model.BookModel
import br.com.kotlin.mercadolivro.model.CustomerModel
import br.com.kotlin.mercadolivro.repository.BookRepository
import br.com.kotlin.mercadolivro.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService (
    private val customerRepository: CustomerRepository,
    private val bookRepository: BookRepository
)  {

    fun create(book: BookModel){
        bookRepository.save(book);
    }

    fun getAll(page: Pageable): Page<BookModel>{
        return bookRepository.findAll(page)
    }

    fun findActives(page: Pageable): Page<BookModel>{
        return bookRepository.findByStatus(BookStatus.ATIVO, page)
    }

    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow{ NotFoundException(Errors.ML101.message.format(id), Errors.ML101.code)}
    }

    fun deleteByCustomer(customer: CustomerModel) {
        var books = bookRepository.findByCustomer(customer)

        for(book in books){
            when(book.status){
                BookStatus.ATIVO ->   book.status = BookStatus.DELETADO
            }

        }

        bookRepository.saveAll(books)
    }

    fun delete(id: Int) {
        if(!bookRepository.existsById(id)){
            throw Exception()
        }
        var book = findById(id)
        book.status = BookStatus.CANCELADO

        update(book)
    }


    fun update(book: BookModel) {
        if(!bookRepository.existsById(book.id!!)){
            throw Exception()
        }
        bookRepository.save(book)
    }

    fun findAllByIds(bookIds: Set<Int>): List<BookModel> {
        return bookRepository.findAllById(bookIds).toList()
    }

    fun purchase(books: MutableList<BookModel>) {

        books.map {
            it.status = BookStatus.VENDIDO
        }

        bookRepository.saveAll(books)
    }

}