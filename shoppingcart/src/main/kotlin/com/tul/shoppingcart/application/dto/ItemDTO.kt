package com.tul.shoppingcart.application.dto

import io.swagger.annotations.ApiModelProperty
import java.util.*

data class ItemDTO(
        @ApiModelProperty(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        val id: UUID,
        val productDTO: ProductDTO,

        @ApiModelProperty(example = "1")
        val quantity: Long,

        @ApiModelProperty(example = "10 USD")
        val totalPrice: String
)