package com.tul.shoppingcart.application.query

import com.tul.shoppingcart.application.dto.ProductDTO
import java.util.*

interface ProductQueryHandler {

    fun findAll(): List<ProductDTO>

    fun findById(id: UUID): ProductDTO?

}