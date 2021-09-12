package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.service.command.EditItemCommandWithIdCommand
import com.tul.shoppingcart.service.command.handler.EditItemHandler
import com.tul.shoppingcart.service.dto.ItemDTO
import com.tul.shoppingcart.service.dto.mapper.toDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class EditItemHandlerImpl(
        private val itemRepository: ItemRepository
) : EditItemHandler {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(EditItemHandlerImpl::class.java)
    }

    override fun execute(@Valid editProductCommand: EditItemCommandWithIdCommand): ItemDTO {
        LOGGER.debug("editing item {}", editProductCommand)

        val item = itemRepository.findById(editProductCommand.id)
                ?: throw ObjectNotFoundException(code = "item_not_found", message = "Item not found with ${editProductCommand.id}")

        item.changeQuantity(editProductCommand.quantity)

        return itemRepository.update(item).toDto()
    }
}