package com.tul.shoppingcart.application.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tul.shoppingcart.application.command.handler.Command
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ApiModel(value = "EditItem")
data class EditItemCommand(
        @JsonIgnore
        var id: UUID?,

        @JsonIgnore
        var shoppingCartId: UUID?,

        @ApiModelProperty(example = "1")
        @field:Min(1, message = "quantity must be greater than 0")
        val quantity: Long
) : Command {
    fun withId(id: UUID): EditItemCommand {
        this.id = id
        return this
    }

    fun withShoppingCartId(shoppingCartId: UUID): EditItemCommand {
        this.shoppingCartId = shoppingCartId
        return this
    }
}