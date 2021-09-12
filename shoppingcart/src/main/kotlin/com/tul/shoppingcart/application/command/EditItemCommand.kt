package com.tul.shoppingcart.application.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tul.shoppingcart.application.command.handler.Command
import io.swagger.annotations.ApiModel
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ApiModel(value = "EditItem")
data class EditItemCommand(
        @JsonIgnore
        var id: UUID?,

        @field:NotNull(message = "Shopping cart id cannot be null")
        val shoppingCartId: UUID,

        @field:Min(1, message = "quantity must be greater than 0")
        val quantity: Long
) : Command {
    fun withId(id: UUID): EditItemCommand {
        this.id = id
        return this
    }
}