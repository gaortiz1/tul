package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.entity.ProductFactory
import com.tul.shoppingcart.domain.entity.Type.SIMPLE
import com.tul.shoppingcart.domain.entity.valueObject.MoneyFactory
import com.tul.shoppingcart.domain.entity.valueObject.SkuFactory
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.service.command.EditProductWithIdCommand
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

internal class EditProductServiceImplTest {

    @InjectMockKs
    private lateinit var editProductServiceUnderTest: EditProductHandlerImpl

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
    fun `should edit a product when it exist in the DB`() {
        val id = UUID.randomUUID()
        val editProductWithIdCommand = EditProductWithIdCommand(
                id,
                "new shoes",
                "new description of shoes",
                BigDecimal.TEN,
                null,
                SIMPLE
        )

        val currentDiscountedProduct = ProductFactory.createDiscountedProduct(
                name = "shoe",
                description = "description",
                price = MoneyFactory.createDenomination(BigDecimal.ONE),
                sku = SkuFactory.create(
                        mapOf(
                                "sh" to "shoes",
                                "m" to "men",
                                "01" to "purple",
                                "size" to "44"
                        )
                ),
        )
        every {
            productRepositoryMock.findById(eq(id))
        } returns currentDiscountedProduct

        every {
            productRepositoryMock.update(
                    match {
                        it.name == "new shoes"
                        it.description == "new description of shoes"
                        it.price.denomination == BigDecimal.TEN
                        it.type == SIMPLE
                    }
            )
        } returns currentDiscountedProduct

        editProductServiceUnderTest.execute(editProductWithIdCommand)

        verify(exactly = 1) {
            productRepositoryMock.findById(any())
            productRepositoryMock.update(any())
        }
    }

    @Test
    fun `should not edit a product when it doest not exist in the DB`() {
        val id = UUID.randomUUID()
        val editProductWithIdCommand = EditProductWithIdCommand(
                id,
                "new shoes",
                "new description of shoes",
                BigDecimal.TEN,
                null,
                SIMPLE
        )

        every {
            productRepositoryMock.findById(eq(id))
        } returns null

        assertThrows<ObjectNotFoundException> {
            editProductServiceUnderTest.execute(editProductWithIdCommand)
        }

        verify(exactly = 1) {
            productRepositoryMock.findById(any())
        }

        verify(exactly = 0) {
            productRepositoryMock.update(any())
        }
    }
}