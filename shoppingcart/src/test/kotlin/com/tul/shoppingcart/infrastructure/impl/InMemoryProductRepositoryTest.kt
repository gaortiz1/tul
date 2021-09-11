package com.tul.shoppingcart.infrastructure.impl

import com.tul.shoppingcart.domain.entity.ProductFactory
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import com.tul.shoppingcart.domain.entity.valueObject.SkuFactory
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class InMemoryProductRepositoryTest {

    @InjectMockKs
    private lateinit var inMemoryProductRepository: InMemoryProductRepository

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should create a new Product`() {
        val newProduct = inMemoryProductRepository.create(
                ProductFactory.createSimpleProduct(
                        name = "shoes",
                        description = "shoes for mem",
                        price = MoneyFactory.zero(),
                        sku = SkuFactory.empty().addAttribute("sh", "shoes").addAttribute("me", "men")
                )
        )

        assertEquals(newProduct, inMemoryProductRepository.findById(newProduct.id))
    }

    @Test
    fun `should update a product when it exist in the DB`() {
        val currentProduct = inMemoryProductRepository.create(
                ProductFactory.createSimpleProduct(
                        name = "shoes",
                        description = "shoes for mem",
                        price = MoneyFactory.zero(),
                        sku = SkuFactory.empty()
                )
        )
        currentProduct.sku = SkuFactory.empty().addAttribute("sh", "shoes").addAttribute("me", "men")
        currentProduct.price = MoneyFactory.createDenomination(BigDecimal.TEN)

        inMemoryProductRepository.update(currentProduct)

        assertEquals(currentProduct, inMemoryProductRepository.findById(currentProduct.id))
    }

    @Test
    fun `should not update a product when it does not exist in the DB`() {
        val currentProduct = ProductFactory.createSimpleProduct(
                name = "shoes",
                description = "shoes for mem",
                price = MoneyFactory.zero(),
                sku = SkuFactory.empty().addAttribute("sh", "shoes").addAttribute("me", "men")
        )

        inMemoryProductRepository.update(currentProduct)

        assertNull(inMemoryProductRepository.findById(currentProduct.id))
    }

    @Test
    fun `should delete a product when it exist in the DB`() {
        val id = inMemoryProductRepository.create(
                ProductFactory.createSimpleProduct(
                        name = "shoes",
                        description = "shoes for mem",
                        price = MoneyFactory.zero(),
                        sku = SkuFactory.empty().addAttribute("sh", "shoes").addAttribute("me", "men")
                )
        ).id

        inMemoryProductRepository.deleteById(id)

        assertNull(inMemoryProductRepository.findById(id))
    }
}