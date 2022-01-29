package br.com.kotlin.mercadolivro.controller.request

import br.com.kotlin.mercadolivro.enums.BookStatus
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty

data class PutBookRequest (

    var name: String?,

    var price: BigDecimal?
)

