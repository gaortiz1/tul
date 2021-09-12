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
        val quantity: Long,
) : Command

@ApiModel(value = "NewItem")
data class NewItemCommandWithShoppingCartId(
        @field:NotNull(message = "Shopping cart cannot be null")
        val shoppingCartId: UUID,
        private val newItemCommand: NewItemCommand,
) : Command {

        constructor(
                shoppingCartId: UUID,
                productId: UUID,
                quantity: Long,
        ) : this(
                shoppingCartId = shoppingCartId,
                newItemCommand = NewItemCommand(
                        productId = productId,
                        quantity = quantity
                )
        )

        val productId: UUID by newItemCommand::productId
        val quantity: Long by newItemCommand::quantity

}