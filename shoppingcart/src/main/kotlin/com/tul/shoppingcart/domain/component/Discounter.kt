package com.tul.shoppingcart.domain.component

import com.tul.shoppingcart.domain.entity.Item
import com.tul.shoppingcart.domain.entity.valueObject.Money

fun interface Discounter {
    fun applyDiscount(item: Item): Money
}

var withoutDiscount = Discounter {
    it.product.price
}