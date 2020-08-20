package com.gugu.dts.course.core.app.routes

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.apiRoute() {
    routing(){
        get("/") {
//            call.respondText("Hello, world!", ContentType.Text.Plain)
//            call.respond(mapOf("key" to "value"))
            call.respond(TestObj())

        }
    }
}

data class TestObj(val name: String = "name", val age: Int = 1)