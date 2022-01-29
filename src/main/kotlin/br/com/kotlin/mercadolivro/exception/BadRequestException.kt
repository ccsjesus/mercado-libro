package br.com.kotlin.mercadolivro.exception

class BadRequestException(
    override val message: String,
    val errorCode: String
) : Exception() {
}