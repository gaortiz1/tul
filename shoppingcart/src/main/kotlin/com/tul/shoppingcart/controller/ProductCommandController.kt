package com.tul.shoppingcart.controller

import com.github.lkqm.spring.api.version.ApiVersion
import com.tul.shoppingcart.service.command.DeleteProductCommand
import com.tul.shoppingcart.service.command.EditProductCommand
import com.tul.shoppingcart.service.command.EditProductWithIdCommand
import com.tul.shoppingcart.service.command.NewProductCommand
import com.tul.shoppingcart.service.command.handler.AddProductHandler
import com.tul.shoppingcart.service.command.handler.DeleteProductHandler
import com.tul.shoppingcart.service.command.handler.EditProductHandler
import com.tul.shoppingcart.service.dto.ProductDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@Api(value = "product", description = "Endpoint for product Endpoint for product management", tags = ["product"])
@RequestMapping("products", produces = ["application/json"], consumes = ["application/json"])
@ApiVersion("1")
class ProductCommandController(
        val addProductService: AddProductHandler,
        val editProductService: EditProductHandler,
        val deleteProductHandler: DeleteProductHandler
) {

    @PostMapping
    @ApiOperation(value = "add new Product", notes = "This method creates a new product")
    fun addProduct(
            @ApiParam(name = "new Product", value = "Model")
            @Valid
            @RequestBody
            newProductCommand: NewProductCommand
    ): ResponseEntity<ProductDTO> = ok(addProductService.execute(newProductCommand))

    @PutMapping("/{id}")
    fun editProduct(
            @ApiParam(name = "edit Product", value = "Model")
            @Valid
            @RequestBody
            editProductCommand: EditProductCommand,
            @PathVariable
            id: UUID,
    ): ResponseEntity<ProductDTO> =
            ok(editProductService.execute(EditProductWithIdCommand(id = id, editProductCommand = editProductCommand)))

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: UUID) {
        deleteProductHandler.execute(DeleteProductCommand(id))
    }
}