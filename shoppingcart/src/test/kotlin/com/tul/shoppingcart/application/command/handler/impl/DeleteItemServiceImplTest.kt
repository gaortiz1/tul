package com.tul.shoppingcart.application.command.handler.impl

import com.tul.shoppingcart.application.command.DeleteCommand
import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import com.tul.shoppingcart.infrastructure.ItemRepository
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
    private lateinit var itemRepository: ItemRepository

    init {
        MockKAnnotations.init(this)
    }

    @BeforeEach
    fun initialize() {
        clearMocks(
                itemRepository,
        )
    }

    @Test
    fun `should delete a item when it exist in the DB`() {
        val id = UUID.randomUUID()
        val deleteProductCommand = DeleteCommand(
                id = id
        )
        every { itemRepository.existsById(eq(id)) } returns true
        justRun { itemRepository.deleteById(eq(id)) }

        deleteProductServiceUnderTest.execute(deleteProductCommand)

        verify(exactly = 1) {
            itemRepository.existsById(any())
            itemRepository.deleteById(any())
        }
    }

    @Test
    fun `should not delete a item when it does not exist in the DB`() {
        val id = UUID.randomUUID()
        val deleteCommand = DeleteCommand(
                id = id
        )
        every { itemRepository.existsById(eq(id)) } returns false

        assertThrows<ObjectNotFoundException> {
            deleteProductServiceUnderTest.execute(deleteCommand)
        }

        verify(exactly = 1) {
            itemRepository.existsById(any())
        }

        verify(exactly = 0) {
            itemRepository.deleteById(any())
        }
    }
}