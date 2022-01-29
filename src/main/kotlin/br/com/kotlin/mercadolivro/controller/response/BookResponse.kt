package br.com.kotlin.mercadolivro.controller.response

import br.com.kotlin.mercadolivro.enums.BookStatus
import br.com.kotlin.mercadolivro.model.CustomerModel
import java.math.BigDecimal

class BookResponse(
    var id: Int? = null,

    var name: String,

    var price: BigDecimal,

    var customer: CustomerModel?,

    var status: BookStatus?
) {

}
