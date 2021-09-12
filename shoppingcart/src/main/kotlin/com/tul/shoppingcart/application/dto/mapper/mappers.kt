package com.tul.shoppingcart.application.dto.mapper

import com.tul.shoppingcart.domain.entity.Item
import com.tul.shoppingcart.domain.entity.Product
import com.tul.shoppingcart.application.dto.ItemDTO
import com.tul.shoppingcart.application.dto.ProductDTO

fun Product.toDto(): ProductDTO = ProductDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price.getValue(),
        sku = this.sku.generate(),
        typeProduct = this.typeProduct
)

fun Item.toDto(): ItemDTO = ItemDTO(
        id =  this.id,
        productDTO = this.product.toDto(),
        quantity = this.quantity.longValueExact(),
        price = this.price().getValue()
)