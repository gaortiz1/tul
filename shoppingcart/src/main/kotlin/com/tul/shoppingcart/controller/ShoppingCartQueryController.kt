package com.tul.shoppingcart.controller

import com.github.lkqm.spring.api.version.ApiVersion
import com.tul.shoppingcart.application.dto.ItemDTO
import com.tul.shoppingcart.application.query.ItemQueryHandler
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Api(value = "shopping-cart", description = "End point for getting shopping cart", tags = ["shoppingCart"])
@RequestMapping("shopping-cart", produces = ["application/json"])
@ApiVersion("1")
class ShoppingCartQueryController(
        val itemQueryHandler: ItemQueryHandler,
) {

    @GetMapping("/{id}/items/{itemId}")
    @ApiOperation(value = "find by id", notes = "This method get a product by id")
    fun findItemByShoppingCartIdAndItemId(
            @PathVariable("id")
            id: UUID,
            @PathVariable("itemId")
            itemId: UUID
    ): ItemDTO? = itemQueryHandler.findItemByShoppingCartIdAndItemId(id, itemId)

    @GetMapping("/{id}/items")
    @ApiOperation(value = "find all", notes = "This method get al the products")
    fun findAll(@PathVariable("id") id: UUID): List<ItemDTO> = itemQueryHandler.findByShoppingCartId(id)
}