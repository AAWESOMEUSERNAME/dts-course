package com.gugu.dts.course.core.inf.features

import io.ebean.Database
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.util.AttributeKey

class Ebean private constructor() {
    companion object EbeanFeature : ApplicationFeature<ApplicationCallPipeline, EbeanFeature.Configuration, EbeanFeature> {

        class Configuration()

        val database
            get() = db
        private lateinit var db: Database

        override val key = AttributeKey<EbeanFeature>("ebean")

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): EbeanFeature {
            val ebeanCfg = DatabaseConfig().apply {
                dataSource = Hikari.dataSource
            }
            db = DatabaseFactory.create(ebeanCfg)
            return this
        }
    }
}