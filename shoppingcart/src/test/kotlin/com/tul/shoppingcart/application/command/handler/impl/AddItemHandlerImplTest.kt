package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.domain.entity.ItemFactory
import com.tul.shoppingcart.domain.exception.ObjectAlreadyExistsException
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.application.command.NewItemCommand
import com.tul.shoppingcart.data.shoesProductDiscount
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import java.util.*

internal class AddItemHandlerImplTest {

    @InjectMockKs
    private lateinit var addItemHandlerImplUnderTest: AddItemHandlerImpl

    @MockK
    private lateinit var productRepositoryMock: ProductRepository

    @MockK
    private lateinit var itemRepositoryMock: ItemRepository

    init {
        MockKAnnotations.init(this)
    }

    @BeforeEach
    fun initialize() {
        clearMocks(
                productRepositoryMock,
                itemRepositoryMock
        )
    }

    @Test
    fun `should create a new item`() {
        val productId = UUID.randomUUID()
        val newItemCommand = NewItemCommand(
                productId = productId,
                quantity = 10
        )

        every { productRepositoryMock.findById(eq(productId)) } returns shoesProductDiscount.copy()
        every { itemRepositoryMock.existsItemByProductId(eq(productId)) } returns false
        every {
            itemRepositoryMock.create(match {
                it.product != null
                it.quantity == BigInteger.TEN
            })
        } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                quantity = 10
        )

        addItemHandlerImplUnderTest.execute(newItemCommand)

        verify(exactly = 1) {
            itemRepositoryMock.create(any())
            itemRepositoryMock.existsItemByProductId(any())
            productRepositoryMock.findById(any())
        }
    }

    @Test
    fun `should not create a new item when a product does not exits`() {
        val productId = UUID.randomUUID()
        val newItemCommand = NewItemCommand(
                productId = productId,
                quantity = 10
        )
        every { productRepositoryMock.findById(eq(productId)) } returns null

        assertThrows<ObjectNotFoundException> {
            addItemHandlerImplUnderTest.execute(newItemCommand)
        }

        verify(exactly = 0) {
            itemRepositoryMock.create(any())
            itemRepositoryMock.findById(any())
        }
        verify(exactly = 1) { productRepositoryMock.findById(any()) }
    }

    @Test
    fun `should not create a new item when item already save`() {
        val productId = UUID.randomUUID()
        val newItemCommand = NewItemCommand(
                productId = productId,
                quantity = 10
        )

        every { productRepositoryMock.findById(eq(productId)) } returns shoesProductDiscount.copy()
        every { itemRepositoryMock.existsItemByProductId(eq(productId)) } returns true

        assertThrows<ObjectAlreadyExistsException> {
            addItemHandlerImplUnderTest.execute(newItemCommand)
        }

        verify(exactly = 0) { itemRepositoryMock.create(any()) }
        verify(exactly = 1) {
            itemRepositoryMock.existsItemByProductId(any())
        }
    }
}