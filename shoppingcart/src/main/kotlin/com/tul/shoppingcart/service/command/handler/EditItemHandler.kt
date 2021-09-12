package com.tul.shoppingcart.service.command.handler

import com.tul.shoppingcart.service.command.CommandService
import com.tul.shoppingcart.service.command.EditItemCommandWithIdCommand
import com.tul.shoppingcart.service.dto.ItemDTO

interface EditItemHandler : CommandService<EditItemCommandWithIdCommand, ItemDTO>