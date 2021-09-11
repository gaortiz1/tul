package com.tul.shoppingcart.controller

import com.github.lkqm.spring.api.version.ApiVersion
import com.tul.shoppingcart.service.dto.ProductDTO
import com.tul.shoppingcart.service.query.ProductQueryHandler
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Api(value = "product", description = "End point for getting products", tags = ["product"])
@RequestMapping("products", produces = ["application/json"])
@ApiVersion("1")
class ProductQueryController(
        val productQueryHandler: ProductQueryHandler,
) {

    @GetMapping("/{id}")
    @ApiOperation(value = "find by id", notes = "This method get a product by id")
    fun findById(@PathVariable id: UUID): ProductDTO? = productQueryHandler.findById(id)

    @GetMapping
    @ApiOperation(value = "find all", notes = "This method get al the products")
    fun findAll(): List<ProductDTO> = productQueryHandler.findAll()
}