package com.tul.shoppingcart.domain.entity

import com.tul.shoppingcart.domain.entity.TypeProduct.DISCOUNT
import com.tul.shoppingcart.domain.entity.TypeProduct.SIMPLE
import com.tul.shoppingcart.domain.entity.valueObject.Money
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import com.tul.shoppingcart.domain.entity.valueObject.Sku
import com.tul.shoppingcart.domain.entity.valueObject.SkuFactory
import java.math.BigDecimal
import java.util.*

data class Product(
        override val id: UUID = UUID.randomUUID(),
        var name: String,
        var description: String?,
        var price: Money,
        var sku: Sku,
        var typeProduct: TypeProduct,
) : EntityId {
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
        this.sku = SkuFactory.createFrom(params)
    }

    private fun makeSimple() {
        this.typeProduct = SIMPLE
    }

    fun changeType(typeProduct: TypeProduct) {
        when (typeProduct) {
            SIMPLE -> makeSimple()
            DISCOUNT -> makeDiscount()
        }
    }

    private fun makeDiscount() {
        this.typeProduct = DISCOUNT
    }

    fun isSimple(): Boolean = this.typeProduct == SIMPLE
}

enum class TypeProduct {
    SIMPLE, DISCOUNT
}

object ProductFactory {
    fun createSimpleProduct(
            name: String,
            description: String?,
            price: Money,
            sku: Sku
    ) = Product(
            id = UUID.randomUUID(),
            name = name,
            description = description,
            price = price,
            sku = sku,
            typeProduct = SIMPLE
    )

    fun createDiscountedProduct(
            name: String,
            description: String?,
            price: Money,
            sku: Sku
    ) = Product(
            name = name,
            description = description,
            price = price,
            sku = sku,
            typeProduct = DISCOUNT
    )
}