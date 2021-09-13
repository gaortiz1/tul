package com.tul.shoppingcart.domain.entity.valueObject

import java.math.BigDecimal
import java.util.*

data class Money(
        var denomination: BigDecimal,
        var currencyCode: String
) {
    fun getValue() = "$denomination $currencyCode"

    private fun multiply(multiplier: Money): Money =
            MoneyFactory.createDenomination(
                    denomination.multiply(multiplier.denomination)
            )

    fun multiply(multiplier: BigDecimal): Money =
            multiply(MoneyFactory.createDenomination(multiplier))

    private fun divide(divisor: Money) : Money =
            MoneyFactory.createDenomination(
                    denomination.divide(divisor.denomination)
            )

    fun divide(divisor: BigDecimal) : Money =
            divide(MoneyFactory.createDenomination(divisor))

    fun add(augend: Money) : Money =
            MoneyFactory.createDenomination(
                    denomination.add(augend.denomination)
            )
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