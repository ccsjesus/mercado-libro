package br.com.kotlin.mercadolivro.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin")
class AdminController() {

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun admin(): String {
        return "This route is admin !"
    }
}