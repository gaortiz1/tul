package com.tul.shoppingcart.domain.entity.valueObject

data class Sku(
        val attributes: MutableSet<SkuAttribute> = mutableSetOf(),
        val separator: String = "-"
) {

    fun addAttribute(code: String, name: String): Sku {
        attributes.add(SkuAttribute(code = code, name = name))
        return this
    }

    fun generate(): String = attributes.joinToString(separator = separator) { it.code.uppercase() }
}

object SkuFactory {

    fun create(params: Map<String, String>): Sku {
        val attributes = params.map {
            SkuAttribute(code = it.key, name = it.value)
        }
        return Sku(attributes = attributes.toMutableSet())
    }

    fun empty(): Sku = Sku()

}