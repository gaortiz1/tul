package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.entity.ProductFactory
import com.tul.shoppingcart.domain.entity.Type.SIMPLE
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import com.tul.shoppingcart.domain.entity.valueObject.SkuFactory
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.service.command.NewProductCommand
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class AddProductServiceImplTest {

    @InjectMockKs
    private lateinit var addProductServiceImplUnderTest: AddProductHandlerImpl

    @MockK
    private lateinit var productRepositoryMock: ProductRepository

    init {
        MockKAnnotations.init(this)
    }

    @BeforeEach
    fun initialize() {
        clearMocks(
                productRepositoryMock,
        )
    }

    @Test
    fun `should create a new Product`() {
        val skuAttributes = mapOf(
                "sh" to "shoes",
                "m" to "men",
                "01" to "purple",
                "size" to "44"
        )

        val newProductCommand = NewProductCommand(
                name = "shoes",
                description = "shoes for mem",
                price = BigDecimal.ONE,
                sku = skuAttributes,
                type = SIMPLE
        )

        every {
            productRepositoryMock.create(
                    match {
                        it.name == "shoes"
                        it.description == "shoes for mem"
                        it.price.denomination == BigDecimal.ONE

                        it.type == SIMPLE
                    }
            )
        } returns ProductFactory.createSimpleProduct(
                name = "shoes",
                description = "shoes for mem",
                price = MoneyFactory.zero(),
                sku = SkuFactory.createFrom(skuAttributes)
        )

        addProductServiceImplUnderTest.execute(newProductCommand)

        verify(exactly = 1) {
            productRepositoryMock.create(any())
        }
    }
}