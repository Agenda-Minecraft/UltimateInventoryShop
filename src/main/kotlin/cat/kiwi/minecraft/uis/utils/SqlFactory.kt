package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.mapper.GoodsMapper
import cat.kiwi.minecraft.uis.mapper.InitDBMapper
import cat.kiwi.minecraft.uis.mapper.PlayerMapper
import cat.kiwi.minecraft.uis.model.entity.GoodPojo
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.github.pagehelper.PageInterceptor
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.datasource.pooled.PooledDataSource
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.transaction.TransactionFactory
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import java.util.*
import javax.sql.DataSource
import kotlin.math.max


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
        var driver = ""
        val url = uisInstance.config.getString("dataSource.url")!!
        val user = uisInstance.config.getString("dataSource.user")!!
        val password = uisInstance.config.getString("dataSource.password")!!
        val poolName = uisInstance.config.getString("hikari.poolName")!!
        val minimumIdle = uisInstance.config.getInt("hikari.minimumIdle")
        val maximumPoolSize = uisInstance.config.getInt("hikari.maximumPoolSize")
        val connectionTimeout = uisInstance.config.getLong("hikari.connectionTimeout")
        val idleTimeout = uisInstance.config.getLong("hikari.idleTimeout")
        val maxLifetime = uisInstance.config.getLong("hikari.maxLifetime")

        driver = if (driverType.lowercase(Locale.getDefault()) == "mysql") {
            "com.mysql.cj.jdbc.Driver"
        } else {
            // TODO implement sqlite
            "com.mysql.cj.jdbc.Driver"
        }
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

    private fun getSqlSessionWithoutBukkit(): SqlSession {
        // 哎呀，就是内网测试数据库，先就放着了，晚点删
        val ds = PooledDataSource()
        ds.driver = "com.mysql.cj.jdbc.Driver"
        ds.url =
            "jdbc:mysql://172.30.50.81:3390/sbtest?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC"
        ds.username = "root"
        ds.password = "z9Kp2P5@g&T3#6kH8+"
        val transactionFactory: TransactionFactory = JdbcTransactionFactory()
        val environment = Environment("development", transactionFactory, ds)
        val configuration = getConfiguration(environment)

        val sqlSessionFactory = SqlSessionFactoryBuilder().build(configuration)

        return sqlSessionFactory.openSession()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val sqlSession = getSqlSessionWithoutBukkit()
        val goodsMapper = sqlSession.getMapper(GoodsMapper::class.java)
        val goods: PageInfo<GoodPojo> =
            PageHelper.startPage<GoodPojo>(1, 40).doSelectPageInfo { goodsMapper.getAllGoods(false) }
    }
}
