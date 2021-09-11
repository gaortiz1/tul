package com.tul.shoppingcart.service.command

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

@ApiModel(value = "DeleteProduct")
data class DeleteProductCommand(
        @ApiModelProperty(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        val id: UUID
) : Command