package com.tul.shoppingcart.service.query

import com.tul.shoppingcart.service.dto.ProductDTO
import java.util.*

interface ProductQueryHandler {

    fun findAll(): List<ProductDTO>

    fun findById(id: UUID): ProductDTO?

}