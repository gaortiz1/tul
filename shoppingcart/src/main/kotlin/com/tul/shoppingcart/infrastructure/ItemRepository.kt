package com.tul.shoppingcart.infrastructure

import com.tul.shoppingcart.domain.entity.Item
import java.util.*

interface ItemRepository : CrudRepository<Item, UUID> {

    fun existsItemByProductId(productId: UUID) : Boolean

}