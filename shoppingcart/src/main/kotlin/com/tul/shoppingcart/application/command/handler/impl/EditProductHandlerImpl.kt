package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.application.command.EditProductWithIdCommand
import com.tul.shoppingcart.application.command.handler.EditProductHandler
import com.tul.shoppingcart.application.dto.ProductDTO
import com.tul.shoppingcart.application.dto.mapper.toDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class EditProductHandlerImpl(
        private val productRepository: ProductRepository
) : EditProductHandler {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(EditProductHandlerImpl::class.java)
    }

    override fun execute(@Valid editProductCommand: EditProductWithIdCommand): ProductDTO {
        LOGGER.debug("editing product {}", editProductCommand)

        val currentProduct = productRepository.findById(editProductCommand.id)
                ?: throw ObjectNotFoundException(code = "product_not_found", message = "Product not found with ${editProductCommand.id}")

        editProductCommand.name?.run {
            currentProduct.changeName(this)
        }

        editProductCommand.description?.run {
            currentProduct.changeDescription(this)
        }

        editProductCommand.price?.run {
            currentProduct.changePrice(this)
        }

        editProductCommand.sku?.run {
            currentProduct.changeSku(this)
        }

        editProductCommand.typeProduct?.run {
            currentProduct.changeType(this)
        }

        return productRepository.update(currentProduct).toDto()
    }
}