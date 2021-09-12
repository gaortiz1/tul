package com.tul.shoppingcart.config

import com.tul.shoppingcart.domain.entity.Item
import com.tul.shoppingcart.domain.entity.Product
import com.tul.shoppingcart.infrastructure.ItemRepository
import com.tul.shoppingcart.infrastructure.ProductRepository
import com.tul.shoppingcart.infrastructure.impl.InMemoryItemRepository
import com.tul.shoppingcart.infrastructure.impl.InMemoryProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
class RepositoryConfig {

    companion object {
        private val PRODUCT_IN_MEMORY_DB = mutableMapOf<UUID, Product>()
        private val ITEM_IN_MEMORY_DB = mutableMapOf<UUID, Item>()
    }

    @Bean
    fun itemRepository(): ItemRepository = InMemoryItemRepository(ITEM_IN_MEMORY_DB)

    @Bean
    fun productRepository(): ProductRepository = InMemoryProductRepository(PRODUCT_IN_MEMORY_DB)

}