package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.DeleteCommand
import com.tul.shoppingcart.application.command.handler.DeleteItemHandler
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.domain.exception.ObjectValidationException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.infrastructure.ShoppingCartRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class DeleteItemHandlerImpl(
        private val itemRepository: ItemRepository,
        private val shoppingCartRepository: ShoppingCartRepository
) : DeleteItemHandler {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(DeleteItemHandlerImpl::class.java)
    }

    override fun execute(@Valid deleteItemCommand: DeleteCommand): Void? {
        LOGGER.debug("deleting item {}", deleteItemCommand)

        val item = itemRepository.findById(deleteItemCommand.id)
                ?: throw ObjectNotFoundException(code = "item_not_found", message = "Item not found with ${deleteItemCommand.id}")

        val shoppingCart = shoppingCartRepository.findById(item.shoppingCartId)
                ?: throw ObjectNotFoundException(code = "shopping_cart_not_found", message = "Shopping cart not found with ${item.shoppingCartId}")

        if (shoppingCart.isCompleted()) {
            throw ObjectValidationException(code = "shopping_cart_was_completed", message = "Shopping cart was completed with ${item.shoppingCartId}")
        }

        itemRepository.deleteById(deleteItemCommand.id)

        return null
    }
}