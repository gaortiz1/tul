package com.tul.shoppingcart.application.query

import com.tul.shoppingcart.application.dto.ItemDTO
import com.tul.shoppingcart.application.dto.ProductDTO
import com.tul.shoppingcart.domain.entity.Item
import java.util.*

interface ItemQueryHandler {

    fun findByShoppingCartId(shoppingCartId: UUID): List<ItemDTO>

    fun findItemByShoppingCartIdAndItemId(shoppingCartId: UUID, itemId: UUID): ItemDTO?

}