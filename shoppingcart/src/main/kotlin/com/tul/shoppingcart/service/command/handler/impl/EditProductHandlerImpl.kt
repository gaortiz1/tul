package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.service.command.EditProductWithIdCommand
import com.tul.shoppingcart.service.command.handler.EditProductHandler
import com.tul.shoppingcart.service.dto.ProductDTO
import com.tul.shoppingcart.service.dto.mapper.toDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EditProductHandlerImpl(
        private val productRepository: ProductRepository
) : EditProductHandler {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(EditProductHandlerImpl::class.java)
    }

    override fun execute(command: EditProductWithIdCommand): ProductDTO {
        LOGGER.info("editing product {}", command)

        val currentProduct = productRepository.findById(command.id)
                ?: throw ObjectNotFoundException(code = "product_not_found", message = "Product not found with ${command.id}")

        command.name?.run {
            currentProduct.changeName(this)
        }

        command.description?.run {
            currentProduct.changeDescription(this)
        }

        command.price?.run {
            currentProduct.changePrice(this)
        }

        command.sku?.run {
            currentProduct.changeSku(this)
        }

        command.type?.run {
            currentProduct.changeType(this)
        }

        return productRepository.update(currentProduct).toDto()
    }
}