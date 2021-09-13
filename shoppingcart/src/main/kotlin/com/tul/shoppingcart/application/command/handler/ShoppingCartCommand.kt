package com.tul.shoppingcart.application.command.handler

import io.swagger.annotations.ApiModel
import java.util.*

@ApiModel(value = "Shopping cart")
data class ShoppingCartCommand(
        val id: UUID
) :  Command