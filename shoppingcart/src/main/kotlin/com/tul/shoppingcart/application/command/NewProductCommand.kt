package com.tul.shoppingcart.application.command

import com.tul.shoppingcart.application.command.handler.Command
import com.tul.shoppingcart.domain.entity.TypeProduct
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ApiModel(value = "NewProduct")
data class NewProductCommand(
        @ApiModelProperty(example = "name")
        @field:NotNull(message = "Name cannot be null")
        @field:Size(min = 2, max = 30, message = "name has a size between 5 and 30")
        val name: String,

        @ApiModelProperty(example = "description")
        @field:NotNull(message = "Description cannot be null")
        @field:Size(max = 250, message = "name has a size between 0 and 30")
        val description: String?,

        @ApiModelProperty(example = "10")
        @field:NotNull(message = "Price cannot be null")
        @field:DecimalMin(value = "0.0", inclusive = false)
        val price: BigDecimal,

        @field:Size(min = 1)
        @Valid
        @ApiModelProperty(
                value = "sku of the product",
                name = "sku",
                example = """{
                            "sh": "shoes",
                            "01": "black",
                            "m": "men"
                          }"""
        )
        val sku: Map<@NotBlank String, @NotBlank String>,

        @field:NotNull(message = "Type cannot be null")
        val typeProduct: TypeProduct
) : Command