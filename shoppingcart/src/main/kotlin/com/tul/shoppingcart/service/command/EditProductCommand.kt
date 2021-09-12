package com.tul.shoppingcart.service.command

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
        @field:Size(min = 5, max = 30, message = "name has a size between 5 and 30")
        val name: String? = null,

        @field:Size(max = 250, message = "name has a size between 0 and 30")
        val description: String? = null,

        @field:DecimalMin(value = "0.0", inclusive = false)
        val price: BigDecimal?,

        @field:Size(min = 1)
        @Valid
        @ApiModelProperty(value = "sku of the product", name = "sku")
        val sku: Map<@NotBlank String, @NotBlank String>?,

        val typeProduct: TypeProduct? = null,
) : Command

data class EditProductWithIdCommand(
        val id: UUID,
        private val editProductCommand: EditProductCommand,
) : Command {

    constructor(
            id: UUID,
            name: String? = null,
            description: String? = null,
            price: BigDecimal?,
            sku: Map<String, String>?,
            typeProduct: TypeProduct? = null,
    ) : this(
            id = id,
            editProductCommand = EditProductCommand(
                    name = name,
                    description = description,
                    price = price,
                    sku = sku,
                    typeProduct = typeProduct
            )
    )

    val name: String? by editProductCommand::name
    val description: String? by editProductCommand::description
    val price: BigDecimal? by editProductCommand::price
    val sku: Map<String, String>? by editProductCommand::sku
    val typeProduct: TypeProduct? by editProductCommand::typeProduct
}