package br.com.kotlin.mercadolivro.enums

enum class Errors(
    val code: String,
    val message: String
) {
    ML001("ML-001","Invalid request"),
    ML101("ML-101","Book [%s] not exists"),
    ML102("ML-102","Cannot update Book with [%s] "),
    ML201("ML-202","Customer [%s] not exists")
}