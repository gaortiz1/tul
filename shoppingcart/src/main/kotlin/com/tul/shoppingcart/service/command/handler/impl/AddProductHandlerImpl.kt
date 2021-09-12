package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.entity.ProductFactory
import com.tul.shoppingcart.domain.entity.TypeProduct.DISCOUNT
import com.tul.shoppingcart.domain.entity.TypeProduct.SIMPLE
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
import javax.validation.Valid

@Service
class AddProductHandlerImpl(
        private val productRepository: ProductRepository
) : AddProductHandler {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(AddProductHandlerImpl::class.java)
    }

    override fun execute(@Valid newProductCommand: NewProductCommand): ProductDTO {
        LOGGER.debug("adding product {}", newProductCommand)

        val newProduct = when (newProductCommand.typeProduct) {
            SIMPLE -> ProductFactory.createSimpleProduct(
                    name = newProductCommand.name,
                    description = newProductCommand.description,
                    price = MoneyFactory.createDenomination(newProductCommand.price),
                    sku = SkuFactory.createFrom(newProductCommand.sku),
            )
            DISCOUNT -> ProductFactory.createDiscountedProduct(
                    name = newProductCommand.name,
                    description = newProductCommand.description,
                    price = MoneyFactory.createDenomination(newProductCommand.price),
                    sku = SkuFactory.createFrom(newProductCommand.sku),
            )
        }

        return productRepository.create(newProduct).toDto()
    }

}