package com.tul.shoppingcart.application.dto

import java.util.*

data class ItemDTO(
        val id: UUID,
        val productDTO: ProductDTO,
        val quantity: Long,
        val totalPrice: String
) {
}