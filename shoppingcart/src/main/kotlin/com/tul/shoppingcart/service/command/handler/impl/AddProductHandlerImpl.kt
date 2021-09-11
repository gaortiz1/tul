package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.entity.ProductFactory
import com.tul.shoppingcart.domain.entity.Type.DISCOUNT
import com.tul.shoppingcart.domain.entity.Type.SIMPLE
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import com.tul.shoppingcart.domain.entity.valueObject.SkuFactory
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.service.command.NewProductCommand
import com.tul.shoppingcart.service.command.handler.AddProductHandler
import com.tul.shoppingcart.service.dto.ProductDTO
import com.tul.shoppingcart.service.dto.mapper.toDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AddProductHandlerImpl(
        private val productRepository: ProductRepository
) : AddProductHandler {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(AddProductHandlerImpl::class.java)
    }

    override fun execute(command: NewProductCommand): ProductDTO {
        LOGGER.info("adding product {}", command)

        val newProduct = when (command.type) {
            SIMPLE -> ProductFactory.createSimpleProduct(
                    name = command.name,
                    description = command.description,
                    price = MoneyFactory.createDenomination(command.price),
                    sku = SkuFactory.create(command.sku),
            )
            DISCOUNT -> ProductFactory.createDiscountedProduct(
                    name = command.name,
                    description = command.description,
                    price = MoneyFactory.createDenomination(command.price),
                    sku = SkuFactory.create(command.sku),
            )
        }

        return productRepository.create(newProduct).toDto()
    }

}