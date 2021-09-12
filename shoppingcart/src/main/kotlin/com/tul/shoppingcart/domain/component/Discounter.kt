package com.tul.shoppingcart.domain.component

import com.tul.shoppingcart.domain.entity.Item
import com.tul.shoppingcart.domain.entity.valueObject.Money
import java.math.BigDecimal

fun interface Discounter {
    fun applyDiscount(item: Item): Money
}

val withoutDiscount = Discounter {
    it.product.price
}

val fiftyPercentDiscount = Discounter {
    if (it.product.isSimple())
        it.product.price
    else
        it.product.price.divide(BigDecimal.valueOf(2))
}