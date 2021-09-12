package com.tul.shoppingcart.domain.entity

import com.tul.shoppingcart.domain.component.Discounter
import com.tul.shoppingcart.domain.component.withoutDiscount
import com.tul.shoppingcart.domain.entity.valueObject.Money
import java.math.BigInteger
import java.util.*

data class Item(
        override val id: UUID = UUID.randomUUID(),
        val product: Product,
        val shoppingCart: ShoppingCart,
        var quantity: BigInteger = BigInteger.ONE
) : EntityId {

    @Transient
    var discounter = withoutDiscount

    fun totalPrice(): Money = discounter.applyDiscount(this).multiply(quantity.toBigDecimal())

    fun changeQuantity(quantity: Long) {
        this.quantity = BigInteger.valueOf(quantity)
    }

    fun applyDiscount(discounter: Discounter): Item {
        this.discounter = discounter
        return this
    }
}

object ItemFactory {

    fun createItem(
            product: Product,
            shoppingCart: ShoppingCart,
            quantity: BigInteger = BigInteger.ONE,
    ) = Item(
            product = product,
            shoppingCart = shoppingCart,
            quantity = quantity
    )

    fun createItem(
            product: Product,
            shoppingCart: ShoppingCart,
            quantity: Long = 1,
    ) = createItem(
            product = product,
            shoppingCart = shoppingCart,
            quantity = BigInteger.valueOf(quantity)
    )

}