package com.tul.shoppingcart.service.command.handler.impl

import com.tul.shoppingcart.domain.entity.ItemFactory
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.service.command.EditItemCommandWithIdCommand
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        val editProductCommand = EditItemCommandWithIdCommand(
                id = itemId,
                quantity = 10
        )

        every { itemRepositoryMock.findById(any()) } returns ItemFactory.createItem(
                product = shoesProductDiscount,
                quantity = 10
        )
        every {
            itemRepositoryMock.update(match {
                it.quantity == 10
            })
        } returns ItemFactory.createItem(
                product = shoesProductDiscount,
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
        val editProductCommand = EditItemCommandWithIdCommand(
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