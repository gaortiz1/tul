package com.tul.shoppingcart.service.command

import java.io.Serializable

interface Command : Serializable

interface CommandService<C : Command, Output> {

    fun execute(command: C): Output?

}