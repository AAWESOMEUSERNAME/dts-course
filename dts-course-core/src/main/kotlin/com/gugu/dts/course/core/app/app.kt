@file:JvmName("AppLauncher")

package com.gugu.dts.course.core.app

import com.gugu.dts.course.core.app.routes.apiRoute
import com.gugu.dts.course.core.inf.features.Ebean
import com.gugu.dts.course.core.inf.features.Hikari
import com.gugu.dts.course.core.inf.pojo.Course
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.LazyDI

@KtorExperimentalAPI
fun Application.main() {
    install(Hikari)
    install(Ebean)
//    di{
//
//    }
//    DIcontainer.init(di())


    val find = Ebean.database.find(Course::class.java, 1)
    println(find)

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

