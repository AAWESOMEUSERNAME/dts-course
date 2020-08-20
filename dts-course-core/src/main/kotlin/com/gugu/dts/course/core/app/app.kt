@file:JvmName("AppLauncher")

package com.gugu.dts.course.core.app

import com.gugu.dts.course.core.app.routes.apiRoute
import com.gugu.dts.course.core.inf.features.Ebean
import com.gugu.dts.course.core.inf.features.Hikari
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.LazyDI

@KtorExperimentalAPI
fun Application.main() {
    install(Hikari)
    install(Ebean)

    install(ContentNegotiation) {
        jackson {
        }
    }

    apiRoute()
}


class PropertyManager private constructor() {
    companion object {
        val cfg: Config by lazy {
            ConfigFactory.load()
        }
    }
}

class DIcontainer private constructor() {
    companion object {
        lateinit var di: LazyDI
        fun init(_di: LazyDI) {
            di = _di
        }
    }
}

