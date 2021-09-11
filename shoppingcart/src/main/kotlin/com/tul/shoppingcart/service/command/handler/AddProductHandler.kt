package com.tul.shoppingcart.service.command.handler

import com.tul.shoppingcart.service.command.CommandService
import com.tul.shoppingcart.service.command.NewProductCommand
import com.tul.shoppingcart.service.dto.ProductDTO

interface AddProductHandler : CommandService<NewProductCommand, ProductDTO>