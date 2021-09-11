package com.tul.shoppingcart.infrastructure

interface CrudRepository<Entity, ID> {

    fun create(entity: Entity): Entity

    fun update(entity: Entity): Entity

    fun deleteById(id: ID)

}