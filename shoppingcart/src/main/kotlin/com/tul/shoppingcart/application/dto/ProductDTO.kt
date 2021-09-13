package com.tul.shoppingcart.application.dto

import com.tul.shoppingcart.domain.entity.TypeProduct
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

data class ProductDTO(
        @ApiModelProperty(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        val id: UUID,

        @ApiModelProperty(example = "name")
        val name: String,

        @ApiModelProperty(example = "description")
        val description: String?,

        @ApiModelProperty(example = "25 USD")
        val price: String,

        @ApiModelProperty(example = "sho-44--bla-men")
        val sku: String,
        val typeProduct: TypeProduct,
)