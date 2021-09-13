package com.tul.shoppingcart.config

import com.tul.shoppingcart.domain.entity.ShoppingCartFactory
import com.tul.shoppingcart.infrastructure.ShoppingCartRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class AppStartupRunner(
        private val shoppingCartRepository: ShoppingCartRepository
) : ApplicationRunner {

    private val shoppingCartId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6")

    companion object {
        val LOGGER: org.slf4j.Logger = LoggerFactory.getLogger(AppStartupRunner::class.java)
    }

    override fun run(args: ApplicationArguments) {
        LOGGER.info("creating a new shopping car with id {}", shoppingCartId)
        shoppingCartRepository.create(ShoppingCartFactory.createOnPendingWithId(shoppingCartId))
    }
}