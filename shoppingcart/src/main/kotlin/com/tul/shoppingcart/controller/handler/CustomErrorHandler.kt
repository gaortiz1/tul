package com.tul.shoppingcart.controller.handler

import com.tul.shoppingcart.domain.exception.ObjectNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import java.time.Instant


@ControllerAdvice
class CustomErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleConstraintViolationException(
            exception: MethodArgumentNotValidException,
            webRequest: ServletWebRequest
    ): ResponseEntity<CustomError> {
        val customError = CustomError(
                code = HttpStatus.BAD_REQUEST.value(),
                date = Instant.now(),
                message = exception.message,
                description = webRequest.getDescription(false)
        )
        return ResponseEntity(customError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ObjectNotFoundException::class)
    fun handleObjectNotFoundException(
            exception: ObjectNotFoundException,
            webRequest: ServletWebRequest
    ): ResponseEntity<CustomError> {
        val customError = CustomError(
                code = HttpStatus.NOT_FOUND.value(),
                date = Instant.now(),
                message = exception.code,
                description = exception.message
        )
        return ResponseEntity(customError, HttpStatus.NOT_FOUND)
    }

}