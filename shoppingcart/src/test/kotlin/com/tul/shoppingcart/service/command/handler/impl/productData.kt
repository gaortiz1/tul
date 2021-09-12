package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.entity.ProductFactory
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import com.tul.shoppingcart.domain.entity.valueObject.SkuFactory

val shoesProductSimple = ProductFactory.createSimpleProduct(
        name = "shoes",
        description = "shoes for mem",
        price = MoneyFactory.zero(),
        sku = SkuFactory.createFrom(
                mapOf(
                        "sh" to "shoes",
                        "m" to "men",
                        "01" to "purple",
                        "size" to "44"
                )
        )
)

val shoesProductDiscount = ProductFactory.createDiscountedProduct(
        name = "shoes",
        description = "shoes for mem",
        price = MoneyFactory.zero(),
        sku = SkuFactory.createFrom(
                mapOf(
                        "sh" to "shoes",
                        "m" to "men",
                        "01" to "purple",
                        "size" to "44"
                )
        )
)
