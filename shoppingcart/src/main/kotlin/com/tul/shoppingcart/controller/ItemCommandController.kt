package com.tul.shoppingcart.controller

import com.github.lkqm.spring.api.version.ApiVersion
import com.tul.shoppingcart.service.command.DeleteCommand
import com.tul.shoppingcart.service.command.EditItemCommand
import com.tul.shoppingcart.service.command.EditItemCommandWithIdCommand
import com.tul.shoppingcart.service.command.NewItemCommand
import com.tul.shoppingcart.service.command.handler.AddItemHandler
import com.tul.shoppingcart.service.command.handler.DeleteItemHandler
import com.tul.shoppingcart.service.command.handler.EditItemHandler
import com.tul.shoppingcart.service.dto.ItemDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@Api(value = "item", description = "Endpoint for item management", tags = ["item"])
@RequestMapping("item", produces = ["application/json"], consumes = ["application/json"])
@ApiVersion("1")
class ItemCommandController(
        val addItemHandler: AddItemHandler,
        val editItemHandler: EditItemHandler,
        val deleteItemHandler: DeleteItemHandler
) {

    @PostMapping
    @ApiOperation(value = "add new Item", notes = "This method creates a new item")
    fun addItem(
            @ApiParam(name = "new Item", value = "Model")
            @Valid
            @RequestBody
            newItemCommand: NewItemCommand
    ): ResponseEntity<ItemDTO> = ok(addItemHandler.execute(newItemCommand))

    @PutMapping("/{id}")
    fun editItem(
            @ApiParam(name = "edit Item", value = "Model")
            @Valid
            @RequestBody
            editItemCommand: EditItemCommand,
            @PathVariable
            id: UUID,
    ): ResponseEntity<ItemDTO> =
            ok(editItemHandler.execute(EditItemCommandWithIdCommand(id = id, editItemCommand = editItemCommand)))

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: UUID) {
        deleteItemHandler.execute(DeleteCommand(id))
    }
}