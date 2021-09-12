package com.tul.shoppingcart.service.dto

import java.math.BigDecimal
import java.util.*

data class ItemDTO(
        val id: UUID,
        val productDTO: ProductDTO,
        val quantity: Int,
        val total: BigDecimal
) {
}