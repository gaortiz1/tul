package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.NewItemCommand
import com.tul.shoppingcart.application.command.handler.AddItemHandler
import com.tul.shoppingcart.application.dto.ItemDTO
import com.tul.shoppingcart.application.dto.mapper.toDto
import com.tul.shoppingcart.domain.entity.ItemFactory
import com.tul.shoppingcart.domain.exception.ObjectValidationException
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.infrastructure.ShoppingCartRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class AddItemHandlerImpl(
        private val itemRepository: ItemRepository,
        private val productRepository: ProductRepository,
        private val shoppingCartRepository: ShoppingCartRepository,
) : AddItemHandler {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(AddItemHandlerImpl::class.java)
    }

    override fun execute(@Valid newItem: NewItemCommand): ItemDTO? {
        LOGGER.debug("adding item {}", newItem)

        val product = productRepository.findById(newItem.productId)
                ?: throw ObjectNotFoundException(code = "item_not_found", message = "Item not found with ${newItem.productId}")

        val shoppingCart = shoppingCartRepository.findById(newItem.shoppingCartId!!)
                ?: throw ObjectNotFoundException(code = "shopping_cart_not_found", message = "Shopping cart not found with ${newItem.shoppingCartId}")

        if (itemRepository.existsItemByProductId(newItem.productId)) {
            throw ObjectValidationException(code = "item_already_exits", message = "Item already exits with ${newItem.productId}")
        }

        if (shoppingCart.isCompleted()) {
            throw ObjectValidationException(code = "shopping_cart_was_completed", message = "Shopping cart was completed with ${newItem.shoppingCartId}")
        }

        val item = ItemFactory.createItem(
                product = product,
                shoppingCart = shoppingCart,
                quantity = newItem.quantity,
        )

        return itemRepository.create(item).toDto()
    }
}