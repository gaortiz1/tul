package com.tul.shoppingcart.application.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tul.shoppingcart.application.command.handler.Command
import io.swagger.annotations.ApiModel
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ApiModel(value = "NewItem")
data class NewItemCommand(
        @JsonIgnore
        var shoppingCartId: UUID?,

        @field:NotNull(message = "Product id cannot be null")
        val productId: UUID,

        @field:Min(1, message = "quantity must be greater than 0")
        val quantity: Long,
) : Command {
        fun withId(shoppingCartId: UUID): NewItemCommand {
                this.shoppingCartId = shoppingCartId
                return this
        }
}