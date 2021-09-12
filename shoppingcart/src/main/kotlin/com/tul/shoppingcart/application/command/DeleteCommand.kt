package com.tul.shoppingcart.application.command

import com.tul.shoppingcart.application.command.handler.Command
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.NotNull

@ApiModel(value = "DeleteProduct")
data class DeleteCommand(
        @ApiModelProperty(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        @field:NotNull
        val id: UUID
) : Command