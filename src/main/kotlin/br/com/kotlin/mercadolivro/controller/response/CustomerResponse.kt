package br.com.kotlin.mercadolivro.controller.response

import br.com.kotlin.mercadolivro.enums.CustomerStatus

class CustomerResponse(
    var id: Int? = null,

    var name: String,

    var email: String,

    var status: CustomerStatus
) {

}
