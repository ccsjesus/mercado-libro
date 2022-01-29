package br.com.kotlin.mercadolivro.controller.request

import br.com.kotlin.mercadolivro.enums.BookStatus
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostBookRequest (

    @field:NotEmpty(message = "Name deve ser informado")
    var name: String,

    @field:NotNull(message = "Price deve ser informado ")
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int,
)

