package com.tul.shoppingcart.application.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tul.shoppingcart.application.command.handler.Command
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ApiModel(value = "NewItem")
data class NewItemCommand(
        @JsonIgnore
        var shoppingCartId: UUID?,

        @ApiModelProperty(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        @field:NotNull(message = "Product id cannot be null")
        val productId: UUID,

        @ApiModelProperty(example = "1")
        @field:Min(1, message = "quantity must be greater than 0")
        val quantity: Long,
) : Command {
        fun withId(shoppingCartId: UUID): NewItemCommand {
                this.shoppingCartId = shoppingCartId
                return this
        }
}