package com.tul.shoppingcart.domain.entity

import com.tul.shoppingcart.domain.entity.ShoppingCartStatus.COMPLETED
import com.tul.shoppingcart.domain.entity.ShoppingCartStatus.WAIT
import java.util.*

data class ShoppingCart(
        override val id: UUID = UUID.randomUUID(),
        var status: ShoppingCartStatus,
) : EntityId {

    fun isCompleted(): Boolean = status == COMPLETED

    fun completed(): ShoppingCart {
        status = COMPLETED
        return this
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
