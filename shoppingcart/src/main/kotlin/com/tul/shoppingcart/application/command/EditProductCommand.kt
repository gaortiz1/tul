package com.tul.shoppingcart.application.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tul.shoppingcart.application.command.handler.Command
import com.tul.shoppingcart.domain.entity.TypeProduct
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ApiModel(value = "EditProduct")
data class EditProductCommand(
        @JsonIgnore
        var id: UUID?,

        @ApiModelProperty(example = "name")
        @field:Size(min = 2, max = 30, message = "name has a size between 5 and 30")
        val name: String? = null,

        @ApiModelProperty(example = "description")
        @field:Size(max = 250, message = "name has a size between 0 and 30")
        val description: String? = null,

        @ApiModelProperty(example = "10")
        @field:DecimalMin(value = "0.0", inclusive = false)
        val price: BigDecimal?,

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
        val sku: Map<@NotBlank String, @NotBlank String>?,

        val typeProduct: TypeProduct? = null,
) : Command {
    fun withId(id: UUID): EditProductCommand {
        this.id = id
        return this
    }
}