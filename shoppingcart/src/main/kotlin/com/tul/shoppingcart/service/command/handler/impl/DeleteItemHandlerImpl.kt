package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.service.command.DeleteCommand
import com.tul.shoppingcart.service.command.handler.DeleteItemHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class DeleteItemHandlerImpl(
        private val itemRepository: ItemRepository
) : DeleteItemHandler {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(DeleteItemHandlerImpl::class.java)
    }

    override fun execute(@Valid deleteItemCommand: DeleteCommand): Void? {
        LOGGER.debug("deleting item {}", deleteItemCommand)

        if (itemRepository.existsById(deleteItemCommand.id).not()) {
            throw ObjectNotFoundException(code = "item_not_found", message = "Item not found with ${deleteItemCommand.id}")
        }

        itemRepository.deleteById(deleteItemCommand.id)

        return null
    }
}