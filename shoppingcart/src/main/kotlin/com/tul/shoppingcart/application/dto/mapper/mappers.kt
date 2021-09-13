package com.tul.shoppingcart.application.dto.mapper

import com.tul.shoppingcart.domain.entity.Item
import com.tul.shoppingcart.domain.entity.Product
import com.tul.shoppingcart.application.dto.ItemDTO
import com.tul.shoppingcart.application.dto.ProductDTO
import com.tul.shoppingcart.application.dto.ShoppingCartDTO
import com.tul.shoppingcart.domain.entity.ShoppingCart

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
        totalPrice = this.totalPrice().getValue()
)

fun ShoppingCart.toDto() : ShoppingCartDTO = ShoppingCartDTO(
        id = this.id,
        status = this.status,
        totalPrice = this.totalPrice.getValue(),
        items = this.items.map(Item::toDto)
)