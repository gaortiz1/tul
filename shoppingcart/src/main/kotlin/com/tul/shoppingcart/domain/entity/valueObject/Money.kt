package com.tul.shoppingcart.domain.entity.valueObject

import java.math.BigDecimal
import java.util.*

data class Money(
        var denomination: BigDecimal,
        var currencyCode: String
) {
    fun getValue() = "$denomination $currencyCode"
}

private val DEFAULT_CURRENCY = Currency.getInstance("USD")

object MoneyFactory {

    fun createDenomination(denomination: BigDecimal): Money =
            Money(
                    denomination = denomination,
                    currencyCode = DEFAULT_CURRENCY.currencyCode
            )

    fun zero(): Money = createDenomination(BigDecimal.ZERO)
}