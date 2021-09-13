package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.NewItemCommand
import com.tul.shoppingcart.data.shoesProductDiscount
import com.tul.shoppingcart.domain.entity.ItemFactory
import com.tul.shoppingcart.domain.entity.ShoppingCartFactory
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.domain.exception.ObjectValidationException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.infrastructure.ShoppingCartRepository
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

    @MockK
    private lateinit var shoppingCartRepositoryMock: ShoppingCartRepository

    init {
        MockKAnnotations.init(this)
    }

    @BeforeEach
    fun initialize() {
        clearMocks(
                productRepositoryMock,
                itemRepositoryMock,
                shoppingCartRepositoryMock
        )
    }

    @Test
    fun `should create a new item`() {
        val productId = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val newItemCommand = NewItemCommand(
                productId = productId,
                shoppingCartId = shoppingCartId,
                quantity = 10
        )

        every { productRepositoryMock.findById(eq(productId)) } returns shoesProductDiscount.copy()
        every { itemRepositoryMock.existsItemByProductId(eq(productId)) } returns false
        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createOnPending()
        every {
            itemRepositoryMock.create(
                    match {
                        it.product != null
                        it.shoppingCartId == shoppingCartId
                        it.quantity == BigInteger.TEN
                    }
            )
        } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCartId = shoppingCartId,
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
        val shoppingCartId = UUID.randomUUID()
        val newItemCommand = NewItemCommand(
                productId = productId,
                shoppingCartId = shoppingCartId,
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
    fun `should not create a new item when a shopping cart does not exits`() {
        val productId = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val newItemCommand = NewItemCommand(
                productId = productId,
                shoppingCartId = shoppingCartId,
                quantity = 10
        )
        every { productRepositoryMock.findById(eq(productId)) } returns shoesProductDiscount.copy()
        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns null

        assertThrows<ObjectNotFoundException> {
            addItemHandlerImplUnderTest.execute(newItemCommand)
        }

        verify(exactly = 0) {
            itemRepositoryMock.create(any())
            itemRepositoryMock.findById(any())
        }
        verify(exactly = 1) {
            productRepositoryMock.findById(any())
            shoppingCartRepositoryMock.findById(any())
        }
    }

    @Test
    fun `should not create a new item when item already save`() {
        val productId = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val newItemCommand = NewItemCommand(
                productId = productId,
                shoppingCartId = shoppingCartId,
                quantity = 10
        )

        every { productRepositoryMock.findById(eq(productId)) } returns shoesProductDiscount.copy()
        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createOnPending()
        every { itemRepositoryMock.existsItemByProductId(eq(productId)) } returns true

        assertThrows<ObjectValidationException> {
            addItemHandlerImplUnderTest.execute(newItemCommand)
        }

        verify(exactly = 0) { itemRepositoryMock.create(any()) }
        verify(exactly = 1) {
            itemRepositoryMock.existsItemByProductId(any())
        }
    }

    @Test
    fun `should not create a new item when shopping cart is completed`() {
        val productId = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val newItemCommand = NewItemCommand(
                productId = productId,
                shoppingCartId = shoppingCartId,
                quantity = 10
        )

        every { productRepositoryMock.findById(eq(productId)) } returns shoesProductDiscount.copy()
        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createCompleted()
        every { itemRepositoryMock.existsItemByProductId(eq(productId)) } returns false

        assertThrows<ObjectValidationException> {
            addItemHandlerImplUnderTest.execute(newItemCommand)
        }

        verify(exactly = 0) { itemRepositoryMock.create(any()) }
        verify(exactly = 1) {
            itemRepositoryMock.existsItemByProductId(any())
            shoppingCartRepositoryMock.findById(any())
        }
    }
}