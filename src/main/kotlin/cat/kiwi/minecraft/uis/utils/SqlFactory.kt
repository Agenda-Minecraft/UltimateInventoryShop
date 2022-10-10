package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.TableConfig
import cat.kiwi.minecraft.uis.mapper.GoodsMapper
import cat.kiwi.minecraft.uis.mapper.InitDBMapper
import cat.kiwi.minecraft.uis.mapper.PlayerMapper
import cat.kiwi.minecraft.uis.model.enum.UisSqlType
import com.github.pagehelper.PageInterceptor
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.transaction.TransactionFactory
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import java.util.*
import javax.sql.DataSource


object SqlFactory {
    fun initDB(): SqlSession {
        val dataSource: DataSource = getDataSource()

        val transactionFactory: TransactionFactory = JdbcTransactionFactory()
        val transactionFactoryProperty = Properties()
        transactionFactoryProperty.setProperty("JDBC.autoCommit", "true")
        transactionFactory.setProperties(transactionFactoryProperty)

        val environment = Environment("development", transactionFactory, dataSource)
        val configuration = getConfiguration(environment)

        val sqlSessionFactory = SqlSessionFactoryBuilder().build(configuration)

        return sqlSessionFactory.openSession()
    }

    private fun getConfiguration(environment: Environment): Configuration {
        val configuration = Configuration(environment)
        val interceptor = PageInterceptor()
        val interceptorProperty = Properties()


        interceptorProperty.setProperty("debug", "true")
        interceptorProperty.setProperty("reasonable", "true")
        interceptor.setProperties(interceptorProperty)

        configuration.addInterceptor(interceptor)
        configuration.addMapper(InitDBMapper::class.java)
        configuration.addMapper(GoodsMapper::class.java)
        configuration.addMapper(PlayerMapper::class.java)
        return configuration
    }

    private fun getDataSource(): DataSource {
        val uisInstance = UltimateInventoryShopPlugin.instance
        val driverType = uisInstance.config.getString("dataSource.driver")!!
        val url = uisInstance.config.getString("dataSource.url")!!
        val driver = when (driverType.lowercase()) {
            "mysql" -> {
                TableConfig.sqlType = UisSqlType.MYSQL
                TableConfig.beenSold = "tinyint(1)"
                TableConfig.dateType = "datetime"
                TableConfig.uidType = "varchar(36)"
                TableConfig.priceType = "decimal(10, 2)"
                TableConfig.nameType = "varchar(40)"
                "com.mysql.cj.jdbc.Driver"
            }
            "postgresql" -> {
                TableConfig.sqlType = UisSqlType.POSTGRESQL
                TableConfig.beenSold = "bool"
                TableConfig.dateType = "date"
                TableConfig.uidType = "uuid"
                TableConfig.priceType = "decimal(10, 2)"
                TableConfig.nameType = "varchar(40)"
                "org.postgresql.Driver"
            }
            else -> {
                TableConfig.sqlType = UisSqlType.SQL_LITE
                TableConfig.beenSold = "real"
                TableConfig.dateType = "real"
                TableConfig.uidType = "text"
                TableConfig.priceType = "real"
                TableConfig.nameType = "text"
                "org.sqlite.JDBC"
            }
        }
        if (driverType.lowercase() == "sqlite") {
            return HikariDataSource().apply {
                this.jdbcUrl = url
                this.driverClassName = driver
            }
        }
        val user = uisInstance.config.getString("dataSource.user")!!
        val password = uisInstance.config.getString("dataSource.password")!!
        val poolName = uisInstance.config.getString("hikari.poolName")!!
        val minimumIdle = uisInstance.config.getInt("hikari.minimumIdle")
        val maximumPoolSize = uisInstance.config.getInt("hikari.maximumPoolSize")
        val connectionTimeout = uisInstance.config.getLong("hikari.connectionTimeout")
        val idleTimeout = uisInstance.config.getLong("hikari.idleTimeout")
        val maxLifetime = uisInstance.config.getLong("hikari.maxLifetime")

        val hds = HikariDataSource()
        hds.isAutoCommit = true
        hds.poolName = poolName
        hds.minimumIdle = minimumIdle
        hds.idleTimeout = idleTimeout
        hds.maximumPoolSize = maximumPoolSize
        hds.maxLifetime = maxLifetime
        hds.connectionTimeout = connectionTimeout

        hds.driverClassName = driver
        hds.jdbcUrl = url
        hds.username = user
        hds.password = password

        return hds
    }
}
