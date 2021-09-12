package com.tul.shoppingcart.domain.entity

import java.math.BigDecimal
import java.util.*

data class Item(
        override val id: UUID = UUID.randomUUID(),
        val product: Product,
        var quantity: Int = 1
) : EntityId {

    fun total(): BigDecimal = BigDecimal.TEN

    fun changeQuantity(quantity: Int) {
        this.quantity = quantity
    }
}

object ItemFactory {

    fun createItem(
            product: Product,
            quantity: Int = 1,
    ) = Item(
            product = product,
            quantity = quantity
    )

}