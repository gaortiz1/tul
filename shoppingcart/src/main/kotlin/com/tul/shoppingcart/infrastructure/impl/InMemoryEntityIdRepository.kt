package com.tul.shoppingcart.infrastructure.impl

import com.tul.shoppingcart.domain.entity.EntityId
import com.tul.shoppingcart.infrastructure.CrudRepository
import java.util.*

abstract class InMemoryEntityIdRepository<Entity: EntityId>(
        val dataBase : MutableMap<UUID, Entity>,
) : CrudRepository<Entity, UUID> {

    override fun create(entity: Entity): Entity {
        dataBase[entity.id] = entity
        return entity
    }

    override fun update(entity: Entity): Entity {
        dataBase.replace(entity.id, entity)
        return entity
    }

    override fun deleteById(id: UUID) {
        dataBase.remove(id)
    }

    override fun findById(id: UUID): Entity? = dataBase[id]

    override fun existsById(id: UUID): Boolean = dataBase.containsKey(id)

    override fun findAll(): List<Entity> = dataBase.values.toList()
}