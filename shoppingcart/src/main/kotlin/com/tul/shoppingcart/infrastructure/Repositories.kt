package com.tul.shoppingcart.infrastructure

import com.tul.shoppingcart.domain.entity.Item
import com.tul.shoppingcart.domain.entity.Product
import java.util.*

interface ItemRepository : CrudRepository<Item, UUID> {

    fun existsItemByProductId(productId: UUID) : Boolean

}

interface ProductRepository : CrudRepository<Product, UUID>