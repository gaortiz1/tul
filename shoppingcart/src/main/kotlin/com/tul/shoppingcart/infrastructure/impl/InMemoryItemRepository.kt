package com.tul.shoppingcart.infrastructure.impl

import com.tul.shoppingcart.domain.entity.Item
import com.tul.shoppingcart.infrastructure.ItemRepository
import java.util.*

class InMemoryItemRepository(dataBase : MutableMap<UUID, Item>) : InMemoryEntityIdRepository<Item>(dataBase), ItemRepository {

    override fun existsItemByProductId(productId: UUID) : Boolean =
            dataBase.filterValues { it.product.id == productId }.isNotEmpty()

}