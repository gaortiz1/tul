package com.tul.shoppingcart.infrastructure

import com.tul.shoppingcart.domain.entity.Product
import java.util.*

interface ProductRepository : CrudRepository<Product, UUID> {

    fun findById(id: UUID): Product?

    fun existsById(id: UUID): Boolean

    fun findAll(): List<Product>

}