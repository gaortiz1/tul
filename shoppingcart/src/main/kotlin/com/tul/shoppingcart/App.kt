package com.tul.shoppingcart

import com.github.lkqm.spring.api.version.EnableApiVersioning
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableApiVersioning
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
