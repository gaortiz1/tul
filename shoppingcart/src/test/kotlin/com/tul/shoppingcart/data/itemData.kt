package com.tul.shoppingcart.data

import com.tul.shoppingcart.domain.entity.ItemFactory
import com.tul.shoppingcart.domain.entity.ProductFactory
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import com.tul.shoppingcart.domain.entity.valueObject.SkuFactory
import java.util.*

val onesShoes = ItemFactory.createItem(
        product = shoesProductSimple.copy(),
        shoppingCartId = UUID.randomUUID(),
        quantity = 1
)

val twoShoesWithDiscount = ItemFactory.createItem(
        product = shoesProductSimple.copy(),
        shoppingCartId = UUID.randomUUID(),
        quantity = 2
)
