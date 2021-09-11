package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.service.command.DeleteProductCommand
import com.tul.shoppingcart.service.command.handler.DeleteProductHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DeleteProductHandlerImpl(
        private val productRepository: ProductRepository
) : DeleteProductHandler {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(DeleteProductHandlerImpl::class.java)
    }

    override fun execute(command: DeleteProductCommand): Void? {
        LOGGER.debug("deleting product {}", command)

        if (productRepository.existsById(command.id).not()) {
            throw ObjectNotFoundException(code = "product_not_found", message = "Product not found with ${command.id}")
        }

        productRepository.deleteById(command.id)

        return null
    }
}