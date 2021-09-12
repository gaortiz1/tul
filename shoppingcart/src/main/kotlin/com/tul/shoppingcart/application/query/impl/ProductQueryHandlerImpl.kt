package com.tul.shoppingcart.application.query.impl

import com.tul.shoppingcart.domain.entity.Product
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.application.dto.ProductDTO
import com.tul.shoppingcart.application.dto.mapper.toDto
import com.tul.shoppingcart.application.query.ProductQueryHandler
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductQueryHandlerImpl(
        private val productRepository: ProductRepository
) : ProductQueryHandler {

    override fun findAll(): List<ProductDTO> = productRepository.findAll().map(Product::toDto)

    override fun findById(id: UUID): ProductDTO? = productRepository.findById(id)?.toDto()
}