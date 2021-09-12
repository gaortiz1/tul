package com.tul.shoppingcart.infrastructure

import com.tul.shoppingcart.domain.entity.Item
import com.tul.shoppingcart.domain.entity.Product
import com.tul.shoppingcart.domain.entity.ShoppingCart
import java.util.*

interface ItemRepository : CrudRepository<Item, UUID> {

    fun existsItemByProductId(productId: UUID) : Boolean

    fun findByShoppingCartId(shoppingCartId: UUID): List<Item>

    fun findItemByShoppingCartIdAndItemId(shoppingCartId: UUID, itemId: UUID): Item?

}

interface ProductRepository : CrudRepository<Product, UUID>

interface ShoppingCartRepository : CrudRepository<ShoppingCart, UUID>