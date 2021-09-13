package com.tul.shoppingcart.application.dto

import com.tul.shoppingcart.domain.entity.ShoppingCartStatus
import java.util.*

data class ShoppingCartDTO(
        val id: UUID,
        val totalPrice: String,
        val status: ShoppingCartStatus,
        val items: List<ItemDTO>
)