package com.tul.shoppingcart.domain.entity

import com.tul.shoppingcart.domain.component.Discounter
import com.tul.shoppingcart.domain.component.withoutDiscount
import com.tul.shoppingcart.domain.entity.valueObject.Money
import java.math.BigInteger
import java.util.*

data class Item(
        override val id: UUID = UUID.randomUUID(),
        val product: Product,
        var quantity: BigInteger = BigInteger.ONE
) : EntityId {

    @Transient
    var discounter = withoutDiscount

    fun price(): Money = discounter.applyDiscount(this).multiply(quantity.toBigDecimal())

    fun changeQuantity(quantity: Long) {
        this.quantity = BigInteger.valueOf(quantity)
    }

    fun applyDiscount(discounter: Discounter) {
        this.discounter = discounter
    }
}

object ItemFactory {

    fun createItem(
            product: Product,
            quantity: BigInteger = BigInteger.ONE,
    ) = Item(
            product = product,
            quantity = quantity
    )

    fun createItem(
            product: Product,
            quantity: Long = 1,
    ) = createItem(
            product = product,
            quantity = BigInteger.valueOf(quantity)
    )

}