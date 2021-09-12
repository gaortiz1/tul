package com.tul.shoppingcart.domain.exception

class ObjectNotFoundException(val code: String, override val message: String) : Exception(message)