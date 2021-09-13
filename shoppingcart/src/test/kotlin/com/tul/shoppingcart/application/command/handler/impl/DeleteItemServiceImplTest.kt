package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.DeleteCommand
import com.tul.shoppingcart.data.shoesProductDiscount
import com.tul.shoppingcart.domain.entity.ItemFactory
import com.tul.shoppingcart.domain.entity.ShoppingCartFactory
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.domain.exception.ObjectValidationException
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.infrastructure.ShoppingCartRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class DeleteItemServiceImplTest {

    @InjectMockKs
    private lateinit var deleteProductServiceUnderTest: DeleteItemHandlerImpl

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
    fun `should delete a item when it exist in the DB`() {
        val id = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val deleteProductCommand = DeleteCommand(
                id = id
        )

        every { itemRepositoryMock.findById(eq(id)) } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCartId = shoppingCartId,
                quantity = 10
        )
        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createOnPending()
        justRun { itemRepositoryMock.deleteById(eq(id)) }

        deleteProductServiceUnderTest.execute(deleteProductCommand)

        verify(exactly = 1) {
            itemRepositoryMock.findById(any())
            itemRepositoryMock.deleteById(any())
            shoppingCartRepositoryMock.findById(any())
        }
    }

    @Test
    fun `should not delete a item when it does not exist in the DB`() {
        val id = UUID.randomUUID()
        val deleteProductCommand = DeleteCommand(
                id = id
        )

        every { itemRepositoryMock.findById(eq(id)) } returns null

        assertThrows<ObjectNotFoundException> {
            deleteProductServiceUnderTest.execute(deleteProductCommand)
        }

        verify(exactly = 1) { itemRepositoryMock.findById(any()) }
        verify(exactly = 0) {
            itemRepositoryMock.deleteById(any())
            shoppingCartRepositoryMock.findById(any())
        }
    }

    @Test
    fun `should not delete a item when shopping cart does not exist in the DB`() {
        val id = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val deleteProductCommand = DeleteCommand(
                id = id
        )

        every { itemRepositoryMock.findById(eq(id)) } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCartId = shoppingCartId,
                quantity = 10
        )
        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns null

        assertThrows<ObjectNotFoundException> {
            deleteProductServiceUnderTest.execute(deleteProductCommand)
        }

        verify(exactly = 1) {
            itemRepositoryMock.findById(any())
            shoppingCartRepositoryMock.findById(any())
        }
        verify(exactly = 0) { itemRepositoryMock.deleteById(any()) }
    }

    @Test
    fun `should not delete a item when shopping cart is completed`() {
        val id = UUID.randomUUID()
        val shoppingCartId = UUID.randomUUID()
        val deleteProductCommand = DeleteCommand(
                id = id
        )

        every { itemRepositoryMock.findById(eq(id)) } returns ItemFactory.createItem(
                product = shoesProductDiscount.copy(),
                shoppingCartId = shoppingCartId,
                quantity = 10
        )
        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createCompleted()

        assertThrows<ObjectValidationException> {
            deleteProductServiceUnderTest.execute(deleteProductCommand)
        }

        verify(exactly = 1) {
            itemRepositoryMock.findById(any())
            shoppingCartRepositoryMock.findById(any())
        }
        verify(exactly = 0) {
            itemRepositoryMock.deleteById(any())
        }
    }
}