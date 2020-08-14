@file:JvmName("AppLauncher")

package com.gugu.dts.course.core.app

import com.gugu.dts.course.core.app.routes.apiRoute
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.util.AttributeKey
import io.ktor.util.KtorExperimentalAPI
import java.time.Duration

@KtorExperimentalAPI
fun Application.main() {

//    val config = this.environment.config
    val config = ConfigFactory.load()


//    install(Hikari){
//        url = config.propertyOrNull("datasource.url").toString()
//        username = config.propertyOrNull("datasource.username").toString()
//        password = config.propertyOrNull("datasource.password").toString()
//        config.propertyOrNull("datasource.poolSize")
//    }

    apiRoute()
}

class Hikari private constructor(val conf: Configuration) {

    val dataSource by lazy {
        generateDataSource(conf);
    }

    class Configuration {
        var url: String? = null
        var username: String? = null
        var password: String? = null
        var driverClassName: String? = null
        var poolSize: Int = 1
        var cachePrepStmts: Boolean = true
        var prepStmtCacheSize: Int = 250
        var prepStmtCacheSqlLimit: Int = 2048
        var isolateInternalQueries: Boolean = false
        var maxLifetime: Duration = Duration.ofMinutes(30)
        var idleTimeout: Duration = Duration.ofMinutes(10)
        var connectionTimeout: Duration = Duration.ofSeconds(30)
        var validationTimeout: Duration = Duration.ofSeconds(5)
        var initializationFailTimeout: Long = 1
        var readOnly: Boolean = false
        var autoCommit: Boolean = true
        var allowPoolSuspension: Boolean = false
        var schema: String = ""
        var catalog: String = ""
        var connectionInitSql: String = ""
        var connectionTestQuery: String = ""
        var transactionIsolation: String? = null
        var registerMbeans: Boolean = false
        var minimumIdle: Int = 1
        var dataSourceProperty: Map<String, Any>? = null
        var extra: MutableMap<String, Configuration> = mutableMapOf()
    }

    private fun generateDataSource(conf: Configuration) = HikariDataSource(HikariConfig().apply {
        conf.driverClassName?.let { driverClassName = it }
        jdbcUrl = conf.url
        username = conf.username
        isIsolateInternalQueries = conf.isolateInternalQueries
        maxLifetime = conf.maxLifetime.toMillis()
        idleTimeout = conf.idleTimeout.toMillis()
        connectionTimeout = conf.connectionTimeout.toMillis()
        initializationFailTimeout = conf.initializationFailTimeout
        isReadOnly = conf.readOnly
        isAutoCommit = conf.autoCommit
        isAllowPoolSuspension = conf.allowPoolSuspension
        isRegisterMbeans = conf.registerMbeans
        transactionIsolation = conf.transactionIsolation
        validationTimeout = conf.validationTimeout.toMillis()
        minimumIdle = conf.minimumIdle
        schema = conf.schema.takeIf { it.isNotBlank() }
        catalog = conf.catalog.takeIf { it.isNotBlank() }
        connectionInitSql = conf.connectionInitSql.takeIf { it.isNotBlank() }
        connectionTestQuery = conf.connectionTestQuery.takeIf { it.isNotBlank() }
        password = conf.password
        maximumPoolSize = conf.poolSize
        addDataSourceProperty(
                "cachePrepStmts",
                conf.cachePrepStmts
        )
        addDataSourceProperty(
                "prepStmtCacheSize",
                conf.prepStmtCacheSize
        )
        addDataSourceProperty(
                "prepStmtCacheSqlLimit",
                conf.prepStmtCacheSqlLimit
        )
        conf.dataSourceProperty?.forEach { t, u ->
            addDataSourceProperty(t, u)
        }
    })

    companion object HikariCPFeature : ApplicationFeature<ApplicationCallPipeline, Hikari.Configuration, Hikari> {

        override val key = AttributeKey<Hikari>("Hikari")

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): Hikari {
            // Call user code to configure a feature
            val configuration = Configuration().apply(configure)
            return Hikari(configuration)
        }
    }
}