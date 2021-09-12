package com.tul.shoppingcart.service.command

import io.swagger.annotations.ApiModel
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ApiModel(value = "NewItem")
data class NewItemCommand(
        @field:NotNull(message = "Product id cannot be null")
        val productId: UUID,

        @field:Min(1, message = "quantity must be greater than 0")
        var quantity: Int,
) : Command