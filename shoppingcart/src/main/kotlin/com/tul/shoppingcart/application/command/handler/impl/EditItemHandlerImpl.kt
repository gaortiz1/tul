package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.EditItemCommand
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.application.command.handler.EditItemHandler
import com.tul.shoppingcart.application.dto.ItemDTO
import com.tul.shoppingcart.application.dto.mapper.toDto
import com.tul.shoppingcart.domain.exception.ObjectValidationException
import com.tul.shoppingcart.infrastructure.ShoppingCartRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class EditItemHandlerImpl(
        private val itemRepository: ItemRepository,
        private val shoppingCartRepository: ShoppingCartRepository
) : EditItemHandler {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(EditItemHandlerImpl::class.java)
    }

    override fun execute(@Valid editProductCommand: EditItemCommand): ItemDTO {
        LOGGER.debug("editing item {}", editProductCommand)

        val item = itemRepository.findById(editProductCommand.id!!)
                ?: throw ObjectNotFoundException(code = "item_not_found", message = "Item not found with ${editProductCommand.id}")

        val shoppingCart = shoppingCartRepository.findById(editProductCommand.shoppingCartId)
                ?: throw ObjectNotFoundException(code = "shopping_cart_not_found", message = "Shopping cart not found with ${editProductCommand.shoppingCartId}")

        if (shoppingCart.isCompleted()) {
            throw ObjectValidationException(code = "shopping_cart_was_completed", message = "Shopping cart was completed with ${editProductCommand.shoppingCartId}")
        }

        item.changeQuantity(editProductCommand.quantity)

        return itemRepository.update(item).toDto()
    }
}