package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.mapper.GoodsMapper
import cat.kiwi.minecraft.uis.mapper.InitDBMapper
import cat.kiwi.minecraft.uis.model.entity.GoodPojo
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.github.pagehelper.PageInterceptor
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
        return configuration
    }

    private fun getDataSource(): DataSource {
        val uisInstance = UltimateInventoryShopPlugin.instance
        val driverType = uisInstance.config.getString("dataSource.driver")!!
        var driver = ""
        val url = uisInstance.config.getString("dataSource.url")!!
        val user = uisInstance.config.getString("dataSource.user")!!
        val password = uisInstance.config.getString("dataSource.password")!!

        driver = if (driverType.lowercase(Locale.getDefault()) == "mysql") {
            "com.mysql.cj.jdbc.Driver"
        } else {
            // TODO implement sqlite
            "com.mysql.cj.jdbc.Driver"
        }

        val ds = PooledDataSource()
        ds.driver = driver
        ds.url = url
        ds.username = user
        ds.password = password
        return ds
    }

    private fun getSqlSessionWithoutBukkit(): SqlSession {
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
        println(goods.list)
    }
}
