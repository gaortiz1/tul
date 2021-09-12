package com.tul.shoppingcart.service.command

import io.swagger.annotations.ApiModel
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ApiModel(value = "EditItem")
data class EditItemCommand(
        @field:NotNull
        @field:Min(1, message = "quantity must be greater than 0")
        val quantity: Int,
) : Command

data class EditItemCommandWithIdCommand(
        val id: UUID,
        private val editItemCommand: EditItemCommand,
) : Command {

    constructor(
            id: UUID,
            quantity: Int,
    ) : this(
            id = id,
            editItemCommand = EditItemCommand(
                    quantity = quantity,
            )
    )

    val quantity: Int by editItemCommand::quantity
}