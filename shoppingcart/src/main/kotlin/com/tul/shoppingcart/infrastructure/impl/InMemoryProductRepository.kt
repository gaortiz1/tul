package com.tul.shoppingcart.infrastructure.impl

import com.tul.shoppingcart.domain.entity.Product
import com.tul.shoppingcart.infrastructure.ProductRepository
import java.util.*

class InMemoryProductRepository(dataBase : MutableMap<UUID, Product>) : InMemoryEntityIdRepository<Product>(dataBase), ProductRepository