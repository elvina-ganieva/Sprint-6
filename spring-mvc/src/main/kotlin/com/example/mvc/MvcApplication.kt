package com.example.mvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ServletComponentScan
class MvcApplication

fun main(args: Array<String>) {
    runApplication<MvcApplication>(*args)
}
