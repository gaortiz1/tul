package com.tul.shoppingcart.service.command.handler

import com.tul.shoppingcart.service.command.CommandService
import com.tul.shoppingcart.service.command.DeleteCommand

interface DeleteItemHandler : CommandService<DeleteCommand, Void>