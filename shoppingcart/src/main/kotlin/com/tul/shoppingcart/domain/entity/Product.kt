package com.tul.shoppingcart.domain.entity

import com.tul.shoppingcart.domain.entity.Type.DISCOUNT
import com.tul.shoppingcart.domain.entity.Type.SIMPLE
import com.tul.shoppingcart.domain.entity.valueObject.Money
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import com.tul.shoppingcart.domain.entity.valueObject.Sku
import com.tul.shoppingcart.domain.entity.valueObject.SkuFactory
import java.math.BigDecimal
import java.util.*

data class Product(
        val id: UUID = UUID.randomUUID(),
        var name: String,
        var description: String?,
        var price: Money,
        var sku: Sku,
        var type: Type,
) {
    fun changeName(name: String) {
        this.name = name
    }

    fun changeDescription(description: String) {
        this.description = description
    }

    fun changePrice(price: BigDecimal) {
        this.price = MoneyFactory.createDenomination(price)
    }

    fun changeSku(params: Map<String, String>) {
        this.sku = SkuFactory.create(params)
    }

    private fun makeSimple() {
        this.type = SIMPLE
    }

    fun changeType(type: Type) {
        when (type) {
            SIMPLE -> makeSimple()
            DISCOUNT -> makeDiscount()
        }
    }

    private fun makeDiscount() {
        this.type = DISCOUNT
    }
}

enum class Type {
    SIMPLE, DISCOUNT
}

object ProductFactory {
    fun createSimpleProduct(
            name: String,
            description: String?,
            price: Money,
            sku: Sku
    ): Product {
        return Product(
                id = UUID.randomUUID(),
                name = name,
                description = description,
                price = price,
                sku = sku,
                type = SIMPLE
        )
    }

    fun createDiscountedProduct(
            name: String,
            description: String?,
            price: Money,
            sku: Sku
    ): Product {
        return Product(
                name = name,
                description = description,
                price = price,
                sku = sku,
                type = DISCOUNT
        )
    }
}