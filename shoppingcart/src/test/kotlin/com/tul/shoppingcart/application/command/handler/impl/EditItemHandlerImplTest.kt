package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.EditItemCommand
import com.tul.shoppingcart.data.shoesProductDiscount
import com.tul.shoppingcart.domain.entity.ItemFactory
import com.tul.shoppingcart.domain.entity.ShoppingCartFactory
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.domain.exception.ObjectValidationException
import com.tul.shoppingcart.infrastructure.ItemRepository
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

internal class EditItemHandlerImplTest {

    @InjectMockKs
    private lateinit var itemHandlerImplUnderTest: EditItemHandlerImpl

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
                itemRepositoryMock,
                shoppingCartRepositoryMock
        )
    }

    @Test
    fun `should update a item when it exits in the DB`() {
        val itemId = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val editProductCommand = EditItemCommand(
                id = itemId,
                quantity = 10,
                shoppingCartId = shoppingCartId
        )

        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createOnWaiting()

        every { itemRepositoryMock.findById(any()) } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCartId = shoppingCartId,
                quantity = 10
        )
        every {
            itemRepositoryMock.update(match {
                it.quantity == BigInteger.TEN
            })
        } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCartId = shoppingCartId,
                quantity = 1
        )

        itemHandlerImplUnderTest.execute(editProductCommand)

        verify(exactly = 1) {
            itemRepositoryMock.update(any())
            itemRepositoryMock.findById(any())
            shoppingCartRepositoryMock.findById(any())
        }
    }

    @Test
    fun `should not update a item when it does not exits`() {
        val itemId = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val editProductCommand = EditItemCommand(
                id = itemId,
                shoppingCartId = shoppingCartId,
                quantity = 10
        )
        every { itemRepositoryMock.findById(eq(itemId)) } returns null

        assertThrows<ObjectNotFoundException> {
            itemHandlerImplUnderTest.execute(editProductCommand)
        }
        verify(exactly = 1) { itemRepositoryMock.findById(any()) }
        verify(exactly = 0) {
            itemRepositoryMock.create(any())
            shoppingCartRepositoryMock.findById(any())
        }
    }

    @Test
    fun `should not update a item when shopping cart does not exits`() {
        val itemId = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val editProductCommand = EditItemCommand(
                id = itemId,
                shoppingCartId = shoppingCartId,
                quantity = 10
        )

        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns null
        every { itemRepositoryMock.findById(eq(itemId)) } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCartId = shoppingCartId,
                quantity = 10
        )

        assertThrows<ObjectNotFoundException> {
            itemHandlerImplUnderTest.execute(editProductCommand)
        }
        verify(exactly = 1) {
            itemRepositoryMock.findById(any())
            shoppingCartRepositoryMock.findById(any())
        }
        verify(exactly = 0) { itemRepositoryMock.create(any()) }
    }

    @Test
    fun `should not update a item when shopping cart is completed`() {
        val itemId = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val editProductCommand = EditItemCommand(
                id = itemId,
                shoppingCartId = shoppingCartId,
                quantity = 10
        )

        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createCompleted()
        every { itemRepositoryMock.findById(eq(itemId)) } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCartId = shoppingCartId,
                quantity = 10
        )

        assertThrows<ObjectValidationException> {
            itemHandlerImplUnderTest.execute(editProductCommand)
        }
        verify(exactly = 1) {
            itemRepositoryMock.findById(any())
            shoppingCartRepositoryMock.findById(any())
        }
        verify(exactly = 0) { itemRepositoryMock.create(any()) }
    }
}