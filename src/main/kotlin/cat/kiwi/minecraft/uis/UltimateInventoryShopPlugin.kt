package cat.kiwi.minecraft.uis


import cat.kiwi.minecraft.uis.command.UISCommands
import cat.kiwi.minecraft.uis.config.ConfigLoader
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.service.GoodsService
import cat.kiwi.minecraft.uis.service.InitDBService
import cat.kiwi.minecraft.uis.service.impl.GoodsServiceImpl
import cat.kiwi.minecraft.uis.service.impl.InitDBServiceImpl
import cat.kiwi.minecraft.uis.utils.SqlFactory
import org.apache.ibatis.session.SqlSession
import org.bukkit.plugin.java.JavaPlugin

class UltimateInventoryShopPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: UltimateInventoryShopPlugin
        lateinit var sqlSession: SqlSession
        lateinit var initDBService: InitDBService
        lateinit var goodsService: GoodsService
    }

    override fun onEnable() {
        instance = this

        ConfigLoader.readConfig(this)
        try {
            sqlSession = SqlFactory.initDB()
        } catch (e: Exception) {
            logger.warning(Lang.sqlError)
            onDisable()
        }
        initDBService = InitDBServiceImpl()
        goodsService = GoodsServiceImpl()

        getCommand("uis")?.setExecutor(UISCommands())

        UltimateInventoryShop(this)
        logger.info("UltimateInventoryShop has been enabled!")
    }


    override fun onDisable() {
        try {
        } catch (e: Exception) {
            e.printStackTrace()
        }
        logger.info("UltimateInventoryShop has been disabled!")
    }
}