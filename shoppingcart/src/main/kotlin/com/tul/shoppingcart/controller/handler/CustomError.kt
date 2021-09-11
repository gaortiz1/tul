package com.tul.shoppingcart.controller.handler

import java.time.Instant

data class CustomError(
        val code: Int,
        val date: Instant,
        val message: String,
        val description: String
)