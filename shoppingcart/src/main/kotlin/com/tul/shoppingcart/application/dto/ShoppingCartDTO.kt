package com.tul.shoppingcart.application.dto

import com.tul.shoppingcart.domain.entity.ShoppingCartStatus
import io.swagger.annotations.ApiModelProperty
import java.util.*


data class ShoppingCartDTO(
        @ApiModelProperty(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        val id: UUID,

        @ApiModelProperty(example = "100 USD")
        val totalPrice: String,
        val status: ShoppingCartStatus,
        val items: List<ItemDTO>
)