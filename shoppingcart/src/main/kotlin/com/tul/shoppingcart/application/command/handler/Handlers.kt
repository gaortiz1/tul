package com.tul.shoppingcart.application.command.handler

import com.tul.shoppingcart.application.command.*
import com.tul.shoppingcart.application.dto.ItemDTO
import com.tul.shoppingcart.application.dto.ProductDTO

interface AddItemHandler : CommandService<NewItemCommand, ItemDTO>

interface AddProductHandler : CommandService<NewProductCommand, ProductDTO>

interface DeleteItemHandler : CommandService<DeleteCommand, Void>

interface DeleteProductHandler : CommandService<DeleteCommand, Void>

interface EditItemHandler : CommandService<EditItemCommandWithIdCommand, ItemDTO>

interface EditProductHandler : CommandService<EditProductWithIdCommand, ProductDTO>