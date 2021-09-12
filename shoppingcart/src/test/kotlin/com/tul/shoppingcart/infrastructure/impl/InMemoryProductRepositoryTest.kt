package com.tul.shoppingcart.infrastructure.impl

import com.tul.shoppingcart.domain.entity.EntityId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

data class EntityTest(
        override val id: UUID = UUID.randomUUID(),
        var name: String
) : EntityId

internal class InMemoryentityRepositoryTest {

    private lateinit var dataBase : MutableMap<UUID, EntityTest>

    private lateinit var inMemoryRepository : InMemoryEntityIdRepository<EntityTest>

    @BeforeEach
    fun initialize() {
        dataBase = mutableMapOf()
        inMemoryRepository = object: InMemoryEntityIdRepository<EntityTest>(dataBase = dataBase){}
    }

    @Test
    fun `should create a new entity`() {
        val newEntity = inMemoryRepository.create(EntityTest(name = "name"))
        assertEquals(newEntity, inMemoryRepository.findById(newEntity.id))
    }

    @Test
    fun `should update a entity when it exist in the DB`() {
        val entityTest = inMemoryRepository.create(EntityTest(name = "name"))

        entityTest.name = "another name"

        inMemoryRepository.update(entityTest)

        assertEquals(entityTest, inMemoryRepository.findById(entityTest.id))
    }

    @Test
    fun `should not update a entity when it does not exist in the DB`() {
        val entityTest = EntityTest(name = "name")

        inMemoryRepository.update(entityTest)

        assertNull(inMemoryRepository.findById(entityTest.id))
    }

    @Test
    fun `should delete a entity when it exist in the DB`() {
        val id = inMemoryRepository.create(EntityTest(name = "name")).id

        inMemoryRepository.deleteById(id)

        assertNull(inMemoryRepository.findById(id))
    }
}