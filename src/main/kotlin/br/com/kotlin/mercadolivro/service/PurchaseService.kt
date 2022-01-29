package br.com.kotlin.mercadolivro.service

import br.com.kotlin.mercadolivro.events.PurchaseEvent
import br.com.kotlin.mercadolivro.model.PurchaseModel
import br.com.kotlin.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun create(purchaseModel: PurchaseModel){
        purchaseRepository.save(purchaseModel)

        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel) )
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel);
    }
}
