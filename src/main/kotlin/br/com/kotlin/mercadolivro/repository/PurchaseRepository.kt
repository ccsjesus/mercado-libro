package br.com.kotlin.mercadolivro.repository

import br.com.kotlin.mercadolivro.model.PurchaseModel
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository : CrudRepository<PurchaseModel, Int> {
}