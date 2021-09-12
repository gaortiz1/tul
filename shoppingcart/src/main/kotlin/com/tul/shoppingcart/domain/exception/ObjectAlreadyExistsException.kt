package com.tul.shoppingcart.domain.exception

class ObjectAlreadyExistsException(val code: String, override val message: String) : Exception(message)