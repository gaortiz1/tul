package com.tul.shoppingcart.application.command

import com.tul.shoppingcart.application.command.handler.Command
import io.swagger.annotations.ApiModel
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ApiModel(value = "EditItem")
data class EditItemCommand(
        @field:Min(1, message = "quantity must be greater than 0")
        val quantity: Long
) : Command

data class EditItemCommandWithIdCommand(
        val id: UUID,
        private val editItemCommand: EditItemCommand,
) : Command {

    constructor(
            id: UUID,
            quantity: Long,
    ) : this(
            id = id,
            editItemCommand = EditItemCommand(
                    quantity = quantity,
            )
    )

    val quantity: Long by editItemCommand::quantity
}