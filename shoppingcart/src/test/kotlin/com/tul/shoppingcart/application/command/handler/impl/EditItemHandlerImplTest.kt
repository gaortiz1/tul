package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.EditItemCommand
import com.tul.shoppingcart.data.shoesProductDiscount
import com.tul.shoppingcart.domain.entity.ItemFactory
import com.tul.shoppingcart.domain.entity.ShoppingCartFactory
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
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

    init {
        MockKAnnotations.init(this)
    }

    @BeforeEach
    fun initialize() {
        clearMocks(
                itemRepositoryMock,
        )
    }

    @Test
    fun `should update a item when it exits in the DB`() {
        val itemId = UUID.randomUUID()
        val editProductCommand = EditItemCommand(
                id = itemId,
                quantity = 10
        )

        every { itemRepositoryMock.findById(any()) } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCart = ShoppingCartFactory.createOnWaiting(),
                quantity = 10
        )
        every {
            itemRepositoryMock.update(match {
                it.quantity == BigInteger.TEN
            })
        } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCart = ShoppingCartFactory.createOnWaiting(),
                quantity = 1
        )

        itemHandlerImplUnderTest.execute(editProductCommand)

        verify(exactly = 1) {
            itemRepositoryMock.update(any())
            itemRepositoryMock.findById(any())
        }
    }

    @Test
    fun `should not update a item when it does not exits`() {
        val itemId = UUID.randomUUID()
        val editProductCommand = EditItemCommand(
                id = itemId,
                quantity = 10
        )
        every { itemRepositoryMock.findById(eq(itemId)) } returns null

        assertThrows<ObjectNotFoundException> {
            itemHandlerImplUnderTest.execute(editProductCommand)
        }
        verify(exactly = 1) { itemRepositoryMock.findById(any()) }
        verify(exactly = 0) { itemRepositoryMock.create(any()) }
    }
}