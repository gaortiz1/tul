package com.tul.shoppingcart.infrastructure.impl

import com.tul.shoppingcart.domain.entity.ShoppingCart
import com.tul.shoppingcart.infrastructure.ShoppingCartRepository
import java.util.*

class InMemoryShoppingCartRepository(dataBase : MutableMap<UUID, ShoppingCart>) : InMemoryEntityIdRepository<ShoppingCart>(dataBase), ShoppingCartRepository