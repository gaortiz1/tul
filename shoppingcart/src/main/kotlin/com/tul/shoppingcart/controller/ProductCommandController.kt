package com.tul.shoppingcart.controller

import com.github.lkqm.spring.api.version.ApiVersion
import com.tul.shoppingcart.application.command.DeleteCommand
import com.tul.shoppingcart.application.command.EditProductCommand
import com.tul.shoppingcart.application.command.NewProductCommand
import com.tul.shoppingcart.application.command.handler.AddProductHandler
import com.tul.shoppingcart.application.command.handler.DeleteProductHandler
import com.tul.shoppingcart.application.command.handler.EditProductHandler
import com.tul.shoppingcart.application.dto.ProductDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@Api(value = "product", description = "Endpoint for product management", tags = ["product"])
@RequestMapping("products")
@ApiVersion("1")
class ProductCommandController(
        val addProductService: AddProductHandler,
        val editProductService: EditProductHandler,
        val deleteProductHandler: DeleteProductHandler
) {

    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    @ApiOperation(value = "add new Product", notes = "This method creates a new product")
    fun addProduct(
            @ApiParam(name = "new Product", value = "Model")
            @Valid
            @RequestBody
            newProductCommand: NewProductCommand
    ): ResponseEntity<ProductDTO> = ok(addProductService.execute(newProductCommand))

    @PutMapping("{id}", produces = ["application/json"], consumes = ["application/json"])
    fun editProduct(
            @ApiParam(name = "edit Product", value = "Model")
            @Valid
            @RequestBody
            editProductCommand: EditProductCommand,
            @PathVariable("id")
            id: UUID,
    ): ResponseEntity<ProductDTO> =
            ok(editProductService.execute(editProductCommand.withId(id)))

    @DeleteMapping("{id}")
    fun deleteProduct(@PathVariable id: UUID) {
        deleteProductHandler.execute(DeleteCommand(id))
    }
}