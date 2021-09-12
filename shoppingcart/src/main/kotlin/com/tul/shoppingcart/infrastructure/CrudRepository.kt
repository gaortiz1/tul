package com.tul.shoppingcart.infrastructure

import java.util.*

interface CrudRepository<Entity, ID> {

    fun create(entity: Entity): Entity

    fun update(entity: Entity): Entity

    fun deleteById(id: ID)

    fun findById(id: UUID): Entity?

    fun existsById(id: UUID): Boolean

    fun findAll(): List<Entity>
}