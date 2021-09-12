package com.tul.shoppingcart.domain.exception

class ObjectValidationException(val code: String, override val message: String) : Exception(message)