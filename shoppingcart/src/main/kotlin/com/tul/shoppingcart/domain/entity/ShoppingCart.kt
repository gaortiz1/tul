package com.tul.shoppingcart.domain.entity

import com.tul.shoppingcart.domain.entity.ShoppingCartStatus.COMPLETED
import com.tul.shoppingcart.domain.entity.ShoppingCartStatus.WAIT
import com.tul.shoppingcart.domain.entity.valueObject.Money
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import java.util.*

data class ShoppingCart(
        override val id: UUID = UUID.randomUUID(),
        var status: ShoppingCartStatus,
        var totalPrice: Money = MoneyFactory.zero(),
        val items: MutableSet<Item> = mutableSetOf()
) : EntityId {

    fun isCompleted(): Boolean = status == COMPLETED

    fun addAllITem(items: List<Item>): ShoppingCart {
        this.items.addAll(items)
        return this
    }

    fun completed() {
        status = COMPLETED
        totalPrice = items.map(Item::totalPrice).reduce(Money::add)
    }
}

enum class ShoppingCartStatus {
    WAIT, COMPLETED
}

object ShoppingCartFactory {
    fun createOnWaiting() = ShoppingCart(
            status = WAIT
    )

    fun createCompleted() = ShoppingCart(
            status = COMPLETED
    )

    fun createOnWaitingWithId(id: UUID) = ShoppingCart(
            id = id,
            status = WAIT
    )
}
