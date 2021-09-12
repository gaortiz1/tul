package com.tul.shoppingcart.application.query.impl

import com.tul.shoppingcart.application.dto.ItemDTO
import com.tul.shoppingcart.application.dto.mapper.toDto
import com.tul.shoppingcart.application.query.ItemQueryHandler
import com.tul.shoppingcart.domain.component.fiftyPercentDiscount
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.infrastructure.ShoppingCartRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemQueryHandlerImpl(
        private val itemRepository: ItemRepository,
        private val shoppingCartRepository: ShoppingCartRepository,
) : ItemQueryHandler {
    override fun findByShoppingCartId(shoppingCartId: UUID): List<ItemDTO> {

        if (shoppingCartRepository.existsById(shoppingCartId).not())
            throw ObjectNotFoundException(code = "shopping_cart_not_found", message = "Shopping cart not found with $shoppingCartId")

        return itemRepository
                .findByShoppingCartId(shoppingCartId)
                .map { it.applyDiscount(fiftyPercentDiscount).toDto() }
                .toList()
    }

    override fun findItemByShoppingCartIdAndItemId(shoppingCartId: UUID, itemId: UUID): ItemDTO? {
        if (shoppingCartRepository.existsById(shoppingCartId).not())
            throw ObjectNotFoundException(code = "shopping_cart_not_found", message = "Shopping cart not found with $shoppingCartId")

        return itemRepository
                .findItemByShoppingCartIdAndItemId(shoppingCartId, itemId)
                ?.applyDiscount(fiftyPercentDiscount)
                ?.toDto()
    }

}