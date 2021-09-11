package com.tul.shoppingcart.service.command.handler

import com.tul.shoppingcart.service.command.CommandService
import com.tul.shoppingcart.service.command.EditProductWithIdCommand
import com.tul.shoppingcart.service.dto.ProductDTO

interface EditProductHandler : CommandService<EditProductWithIdCommand, ProductDTO>