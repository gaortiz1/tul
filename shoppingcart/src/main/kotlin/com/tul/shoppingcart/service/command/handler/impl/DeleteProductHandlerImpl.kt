package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.service.command.DeleteCommand
import com.tul.shoppingcart.service.command.handler.DeleteProductHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class DeleteProductHandlerImpl(
        private val productRepository: ProductRepository
) : DeleteProductHandler {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(DeleteProductHandlerImpl::class.java)
    }

    override fun execute(@Valid deleteProductCommand: DeleteCommand): Void? {
        LOGGER.debug("deleting product {}", deleteProductCommand)

        if (productRepository.existsById(deleteProductCommand.id).not()) {
            throw ObjectNotFoundException(code = "product_not_found", message = "Product not found with ${deleteProductCommand.id}")
        }

        productRepository.deleteById(deleteProductCommand.id)

        return null
    }
}