package com.tul.shoppingcart.service.command.handler

import com.tul.shoppingcart.service.command.CommandService
import com.tul.shoppingcart.service.command.DeleteProductCommand

interface DeleteProductHandler : CommandService<DeleteProductCommand, Void>