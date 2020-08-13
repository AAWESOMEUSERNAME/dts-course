package com.gugu.dts.course.core.app.routes

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.apiRoute() {
    routing(){
        get("/") {
            call.respondText("Hello, world!", ContentType.Text.Html)
        }
    }
}