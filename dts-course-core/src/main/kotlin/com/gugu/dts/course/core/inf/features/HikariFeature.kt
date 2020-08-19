package com.gugu.dts.course.core.inf.features

import com.gugu.dts.course.core.app.PropertyManager
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.config4k.extract
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.util.AttributeKey
import java.time.Duration

class Hikari private constructor() {
    companion object HikariCPFeature : ApplicationFeature<ApplicationCallPipeline, HikariCPFeature.Configuration, HikariCPFeature> {

        override val key = AttributeKey<HikariCPFeature>("Hikari")

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): HikariCPFeature {
            return HikariCPFeature
        }

        val dataSource by lazy {
            generateDataSource()
        }

        data class Configuration(
                val url: String,
                val username: String? = null,
                val password: String? = null,
                val driverClassName: String? = null,
                val poolSize: Int = 1,
                val cachePrepStmts: Boolean = true,
                val prepStmtCacheSize: Int = 250,
                val prepStmtCacheSqlLimit: Int = 2048,
                val isolateInternalQueries: Boolean = false,
                val maxLifetime: Duration = Duration.ofMinutes(30),
                val idleTimeout: Duration = Duration.ofMinutes(10),
                val connectionTimeout: Duration = Duration.ofSeconds(30),
                val validationTimeout: Duration = Duration.ofSeconds(5),
                val initializationFailTimeout: Long = 1,
                val readOnly: Boolean = false,
                val autoCommit: Boolean = true,
                val allowPoolSuspension: Boolean = false,
                val schema: String = "",
                val catalog: String = "",
                val connectionInitSql: String = "",
                val connectionTestQuery: String = "",
                val transactionIsolation: String? = null,
                val registerMbeans: Boolean = false,
                val minimumIdle: Int = 1,
                val dataSourceProperty: Map<String, Any>? = null
        )


        private fun generateDataSource() = HikariDataSource(HikariConfig().apply {
            val conf = PropertyManager.cfg.extract<Configuration>("Hikari")
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
    }
}