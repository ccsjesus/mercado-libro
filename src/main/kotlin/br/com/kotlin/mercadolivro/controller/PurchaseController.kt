package br.com.kotlin.mercadolivro.controller

import br.com.kotlin.mercadolivro.controller.mapper.PurchaseMapper
import br.com.kotlin.mercadolivro.controller.request.PostPurchaseRequest
import br.com.kotlin.mercadolivro.model.PurchaseModel
import br.com.kotlin.mercadolivro.service.PurchaseService
import io.swagger.annotations.ApiModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("purchases")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody request: PostPurchaseRequest) {
        purchaseService.create(purchaseMapper.toModel(request))
    }
}