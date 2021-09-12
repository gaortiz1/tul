package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.entity.ItemFactory
import com.tul.shoppingcart.domain.exception.ObjectAlreadyExistsException
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.service.command.NewItemCommand
import com.tul.shoppingcart.service.command.handler.AddItemHandler
import com.tul.shoppingcart.service.dto.ItemDTO
import com.tul.shoppingcart.service.dto.mapper.toDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class AddItemHandlerImpl(
        private val itemRepository: ItemRepository,
        private val productRepository: ProductRepository,
) : AddItemHandler {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(AddItemHandlerImpl::class.java)
    }

    override fun execute(@Valid newItem: NewItemCommand): ItemDTO? {
        LOGGER.debug("adding item {}", newItem)

        val product = productRepository.findById(newItem.productId)
                ?: throw ObjectNotFoundException(code = "item_not_found", message = "Item not found with ${newItem.productId}")

        if (itemRepository.existsItemByProductId(newItem.productId)) {
            throw ObjectAlreadyExistsException(code = "item_already_exits", message = "Item already exits with ${newItem.productId}")
        }

        val item = ItemFactory.createItem(
                product = product,
                quantity = newItem.quantity
        )

        return itemRepository.create(item).toDto()
    }
}