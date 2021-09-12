package com.tul.shoppingcart.application.command

import com.tul.shoppingcart.application.command.handler.Command
import io.swagger.annotations.ApiModel
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ApiModel(value = "NewItem")
data class NewItemCommand(
        @field:NotNull(message = "Product id cannot be null")
        val productId: UUID,

        @field:Min(1, message = "quantity must be greater than 0")
        var quantity: Long,
) : Command