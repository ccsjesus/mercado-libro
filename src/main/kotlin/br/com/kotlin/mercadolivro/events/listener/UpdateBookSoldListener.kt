package br.com.kotlin.mercadolivro.events.listener

import br.com.kotlin.mercadolivro.events.PurchaseEvent
import br.com.kotlin.mercadolivro.service.BookService
import br.com.kotlin.mercadolivro.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import java.util.*

class UpdateBookSoldListener (
    private val bookService: BookService
) {

    @Async
    @EventListener
    fun listen(purchaseEvent: PurchaseEvent){
        bookService.purchase(purchaseEvent.purchaseModel.books)

    }
}