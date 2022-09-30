package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.mapper.InitDBMapper
import org.apache.ibatis.datasource.pooled.PooledDataSource
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
        val environment = Environment("development", transactionFactory, dataSource)
        val configuration = Configuration(environment)
        configuration.addMapper(InitDBMapper::class.java)
        val sqlSessionFactory = SqlSessionFactoryBuilder().build(configuration)

        return sqlSessionFactory.openSession()
    }

    private fun getDataSource(): DataSource {
        val uisInstance = UltimateInventoryShopPlugin.instance
        val driverType = uisInstance.config.getString("dataSource.driver")!!
        var driver = ""
        val url = uisInstance.config.getString("dataSource.url")!!
        val user = uisInstance.config.getString("dataSource.user")!!
        val password = uisInstance.config.getString("dataSource.password")!!

        driver = if (driverType.lowercase(Locale.getDefault()) == "mysql") {
            "com.mysql.jdbc.Driver"
        } else {
            // TODO implement sqlite
            "com.mysql.jdbc.Driver"
        }

        val ds = PooledDataSource()
        ds.driver = driver
        ds.url = url
        ds.username = user
        ds.password = password
        return ds
    }
}
