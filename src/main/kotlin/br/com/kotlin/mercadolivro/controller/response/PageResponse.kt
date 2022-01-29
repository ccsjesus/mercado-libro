package br.com.kotlin.mercadolivro.controller.response

class PageResponse<T>(
    private val itens: List<T>,
    private val currentPage: Int,
    private val totalItens: Long,
    private val totalPages: Int
)