package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.handler.CheckoutShoppingCart
import com.tul.shoppingcart.application.command.handler.ShoppingCartCommand
import com.tul.shoppingcart.application.dto.ShoppingCartDTO
import com.tul.shoppingcart.application.dto.mapper.toDto
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.domain.exception.ObjectValidationException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.infrastructure.ShoppingCartRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CheckoutShoppingCartImpl(
        private val shoppingCartRepository: ShoppingCartRepository,
        private val itemRepository: ItemRepository,
) : CheckoutShoppingCart {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(CheckoutShoppingCartImpl::class.java)
    }

    override fun execute(command: ShoppingCartCommand): ShoppingCartDTO? {
        LOGGER.debug("checking out shopping cart with id {}", command)

        val shoppingCart = shoppingCartRepository.findById(command.id)
                ?: throw ObjectNotFoundException(code = "shopping_cart_not_found", message = "Shopping cart not found with ${command.id}")

        if (shoppingCart.isCompleted()) {
            throw ObjectValidationException(code = "shopping_cart_was_completed", message = "Shopping cart was completed with ${command.id}")
        }

        val items = itemRepository.findByShoppingCartId(command.id)

        if (items.isEmpty()) {
            throw ObjectValidationException(code = "shopping_cart_has_not_item", message = "Shopping cart has not item")
        }

        shoppingCart.addAllITem(items).completed()

        return shoppingCartRepository.update(shoppingCart).toDto()
    }
}