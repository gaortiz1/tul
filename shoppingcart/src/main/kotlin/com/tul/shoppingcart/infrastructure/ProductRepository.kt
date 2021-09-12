package com.tul.shoppingcart.infrastructure

import com.tul.shoppingcart.domain.entity.Product
import java.util.*

interface ProductRepository : CrudRepository<Product, UUID>