package com.tul.shoppingcart.domain.entity

import com.tul.shoppingcart.data.shoesProductSimple
import com.tul.shoppingcart.domain.component.Discounter
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

internal class ItemTest {

    @Test
    fun `should get total without discount`() {

        val item = ItemFactory.createItem(
                product = shoesProductSimple.copy(
                        price = MoneyFactory.createDenomination(BigDecimal.TEN)
                ),
                shoppingCartId = UUID.randomUUID(),
                quantity = 5
        )

        assertEquals(BigDecimal.valueOf(50), item.totalPrice().denomination)
    }

    @Test
    fun `should get total with discount`() {
        val item = ItemFactory.createItem(
                product = shoesProductSimple.copy(
                        price = MoneyFactory.createDenomination(BigDecimal.TEN)
                ),
                shoppingCartId = UUID.randomUUID(),
                quantity = 5
        )

        val fiftyPercentDiscount = Discounter {
            it.product.price.divide(BigDecimal.valueOf(2))
        }

        item.applyDiscount(fiftyPercentDiscount)

        assertEquals(BigDecimal.valueOf(25), item.totalPrice().denomination)
    }
}