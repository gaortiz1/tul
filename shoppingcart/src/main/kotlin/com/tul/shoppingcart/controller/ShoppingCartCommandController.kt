package com.tul.shoppingcart.controller

import com.github.lkqm.spring.api.version.ApiVersion
import com.tul.shoppingcart.application.command.*
import com.tul.shoppingcart.application.command.handler.AddItemHandler
import com.tul.shoppingcart.application.command.handler.DeleteItemHandler
import com.tul.shoppingcart.application.command.handler.EditItemHandler
import com.tul.shoppingcart.application.dto.ItemDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@Api(value = "shopping-cart", description = "Endpoint for item management", tags = ["shoppingCart"])
@RequestMapping("shopping-cart", produces = ["application/json"], consumes = ["application/json"])
@ApiVersion("1")
class ShoppingCartCommandController(
        val addItemHandler: AddItemHandler,
        val editItemHandler: EditItemHandler,
        val deleteItemHandler: DeleteItemHandler
) {

    @PostMapping("/{id}/items")
    @ApiOperation(value = "add new Item", notes = "This method creates a new item")
    fun addItem(
            @ApiParam(name = "new Item", value = "Model")
            @Valid
            @RequestBody
            newItemCommand: NewItemCommand,
            @PathVariable("id")
            id: UUID,
    ): ResponseEntity<ItemDTO> = ok(
            addItemHandler.execute(newItemCommand.withId(id))
    )

    @PutMapping("/{id}/items/{itemId}")
    @ApiOperation(value = "edit new Item", notes = "This method edites a new item")
    fun editItem(
            @PathVariable("id")
            id: UUID,
            @PathVariable("itemId")
            itemId: UUID,
            @ApiParam(name = "edit Item", value = "Model")
            @Valid
            @RequestBody
            editItemCommand: EditItemCommand,
    ): ResponseEntity<ItemDTO> =
            ok(editItemHandler.execute(editItemCommand.withId(itemId)))


    @DeleteMapping("/{id}/items/{itemId}")
    fun deleteItem(@PathVariable("id") id: UUID,  @PathVariable("itemId") itemId: UUID) {
        deleteItemHandler.execute(DeleteCommand(itemId))
    }
}