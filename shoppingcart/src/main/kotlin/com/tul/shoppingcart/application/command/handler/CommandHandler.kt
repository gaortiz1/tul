package com.tul.shoppingcart.application.command.handler

import java.io.Serializable

interface Command : Serializable

interface CommandService<C : Command, Output> {

    fun execute(command: C): Output?

}