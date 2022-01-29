package br.com.kotlin.mercadolivro.controller

import br.com.kotlin.mercadolivro.controller.request.PostCustomerRequest
import br.com.kotlin.mercadolivro.controller.request.PutCustomerRequest
import br.com.kotlin.mercadolivro.controller.response.CustomerResponse
import br.com.kotlin.mercadolivro.extension.toCustomerModel
import br.com.kotlin.mercadolivro.extension.toResponse
import br.com.kotlin.mercadolivro.model.CustomerModel
import br.com.kotlin.mercadolivro.security.UserCanOnlyAccessTheirOwnResource
import br.com.kotlin.mercadolivro.service.CustomerService
import io.swagger.annotations.ApiModel
import javafx.concurrent.Service
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerController(
    val customerService: CustomerService
) {

    @GetMapping
    fun getAll(@RequestParam name: String? ): List<CustomerResponse> {
        return customerService.getAll(name).map { it.toResponse() };
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid customer: PostCustomerRequest) {
        customerService.create(customer.toCustomerModel());
    }

    @GetMapping("/{id}")
    @UserCanOnlyAccessTheirOwnResource
    fun getCustomer(@PathVariable id: Int): CustomerResponse{
        return customerService.getCustomer(id).toResponse();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCustomer(@PathVariable id: Int, @RequestBody @Valid request: PutCustomerRequest){
         var customer = customerService.getById(id);
         customerService.updateCustomer(request.toCustomerModel(customer));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Int, @RequestBody @Valid request: PutCustomerRequest){
        var customer = customerService.getById(id);
        customerService.deleteCustomer(request.toCustomerModel(customer));
    }
}