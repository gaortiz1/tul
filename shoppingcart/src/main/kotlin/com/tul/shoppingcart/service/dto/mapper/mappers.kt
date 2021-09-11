package com.tul.shoppingcart.service.dto.mapper

import com.tul.shoppingcart.domain.entity.Product
import com.tul.shoppingcart.service.dto.ProductDTO

fun Product.toDto(): ProductDTO = ProductDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price.getValue(),
        sku = this.sku.generate(),
        type = this.type
)