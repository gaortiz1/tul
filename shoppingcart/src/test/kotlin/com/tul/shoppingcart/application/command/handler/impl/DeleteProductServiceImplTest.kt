package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.application.command.DeleteCommand
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class DeleteProductServiceImplTest {

    @InjectMockKs
    private lateinit var deleteProductServiceUnderTest: DeleteProductHandlerImpl

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
    fun `should delete a product when it exist in the DB`() {
        val id = UUID.randomUUID()
        val deleteProductCommand = DeleteCommand(
                id = id
        )
        every { productRepositoryMock.existsById(eq(id)) } returns true
        justRun { productRepositoryMock.deleteById(eq(id)) }

        deleteProductServiceUnderTest.execute(deleteProductCommand)

        verify(exactly = 1) {
            productRepositoryMock.existsById(any())
            productRepositoryMock.deleteById(any())
        }
    }

    @Test
    fun `should not delete a product when it does not exist in the DB`() {
        val id = UUID.randomUUID()
        val deleteCommand = DeleteCommand(
                id = id
        )
        every { productRepositoryMock.existsById(eq(id)) } returns false

        assertThrows<ObjectNotFoundException> {
            deleteProductServiceUnderTest.execute(deleteCommand)
        }

        verify(exactly = 1) {
            productRepositoryMock.existsById(any())
        }

        verify(exactly = 0) {
            productRepositoryMock.deleteById(any())
        }
    }
}