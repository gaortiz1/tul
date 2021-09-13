package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.handler.ShoppingCartCommand
import com.tul.shoppingcart.data.onesShoes
import com.tul.shoppingcart.data.twoShoesWithDiscount
import com.tul.shoppingcart.domain.entity.ShoppingCartFactory
import com.tul.shoppingcart.domain.entity.ShoppingCartStatus
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
import java.util.*

internal class CheckoutShoppingCartImplTest {

    @InjectMockKs
    private lateinit var checkoutShoppingCartUnderTest: CheckoutShoppingCartImpl

    @MockK
    private lateinit var shoppingCartRepositoryMock: ShoppingCartRepository

    @MockK
    private lateinit var itemRepositoryMock: ItemRepository

    init {
        MockKAnnotations.init(this)
    }

    @BeforeEach
    fun initialize() {
        clearMocks(
                shoppingCartRepositoryMock,
                itemRepositoryMock
        )
    }

    @Test
    fun `should check out a shopping cart when it has items and is not completed`() {
        val shoppingCartId = UUID.randomUUID()
        val shoppingCartCommand = ShoppingCartCommand(
                id = shoppingCartId
        )

        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createOnWaiting()
        every { itemRepositoryMock.findByShoppingCartId(eq(shoppingCartId)) } returns listOf(onesShoes, twoShoesWithDiscount)
        every {
            shoppingCartRepositoryMock.update(
                    match {
                        it.status == ShoppingCartStatus.COMPLETED
                        it.items.size == 2
                    }
            )
        } returns ShoppingCartFactory.createCompleted()

        checkoutShoppingCartUnderTest.execute(shoppingCartCommand)

        verify(exactly = 1) {
            shoppingCartRepositoryMock.findById(any())
            itemRepositoryMock.findByShoppingCartId(any())
            shoppingCartRepositoryMock.update(any())
        }
    }

    @Test
    fun `should not check out a shopping cart when it does not exits in the DB`() {
        val shoppingCartId = UUID.randomUUID()
        val shoppingCartCommand = ShoppingCartCommand(
                id = shoppingCartId
        )

        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns null

        assertThrows<ObjectNotFoundException> {
            checkoutShoppingCartUnderTest.execute(shoppingCartCommand)
        }

        verify(exactly = 1) { shoppingCartRepositoryMock.findById(any()) }
        verify(exactly = 0) {
            itemRepositoryMock.findByShoppingCartId(any())
            shoppingCartRepositoryMock.update(any())
        }
    }

    @Test
    fun `should not check out a shopping cart when it is completed`() {
        val shoppingCartId = UUID.randomUUID()
        val shoppingCartCommand = ShoppingCartCommand(
                id = shoppingCartId
        )

        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createCompleted()

        assertThrows<ObjectValidationException> {
            checkoutShoppingCartUnderTest.execute(shoppingCartCommand)
        }

        verify(exactly = 1) { shoppingCartRepositoryMock.findById(any()) }
        verify(exactly = 0) {
            itemRepositoryMock.findByShoppingCartId(any())
            shoppingCartRepositoryMock.update(any())
        }
    }

    @Test
    fun `should not check out a shopping cart when it has not item`() {
        val shoppingCartId = UUID.randomUUID()
        val shoppingCartCommand = ShoppingCartCommand(
                id = shoppingCartId
        )

        every { shoppingCartRepositoryMock.findById(eq(shoppingCartId)) } returns ShoppingCartFactory.createOnWaiting()
        every { itemRepositoryMock.findByShoppingCartId(eq(shoppingCartId)) } returns emptyList()


        assertThrows<ObjectValidationException> {
            checkoutShoppingCartUnderTest.execute(shoppingCartCommand)
        }

        verify(exactly = 1) {
            shoppingCartRepositoryMock.findById(any())
            itemRepositoryMock.findByShoppingCartId(any())
        }
        verify(exactly = 0) { shoppingCartRepositoryMock.update(any()) }
    }
}