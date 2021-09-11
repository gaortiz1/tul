package com.tul.shoppingcart.infrastructure.impl

import com.tul.shoppingcart.domain.entity.Product
import com.tul.shoppingcart.infrastructure.ProductRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryProductRepository : ProductRepository {

    companion object {
        private val IN_MEMORY_DB = mutableMapOf<UUID, Product>()
    }

    override fun create(product: Product): Product {
        IN_MEMORY_DB[product.id] = product
        return product
    }

    override fun update(product: Product): Product {
        IN_MEMORY_DB.replace(product.id, product)
        return product
    }


    override fun deleteById(id: UUID) {
        IN_MEMORY_DB.remove(id)
    }

    override fun findById(id: UUID): Product? = IN_MEMORY_DB[id]?.copy()

    override fun existsById(id: UUID): Boolean = IN_MEMORY_DB.containsKey(id)

    override fun findAll(): List<Product> = IN_MEMORY_DB.values.toList()

}