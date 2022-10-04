package cat.kiwi.minecraft.uis


import cat.kiwi.minecraft.uis.command.UISCommands
import cat.kiwi.minecraft.uis.config.ConfigLoader
import cat.kiwi.minecraft.uis.utils.SqlFactory
import org.apache.ibatis.session.SqlSession
import org.bukkit.plugin.java.JavaPlugin

class UltimateInventoryShopPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: UltimateInventoryShopPlugin
        lateinit var sqlSession: SqlSession
    }

    override fun onEnable() {
        instance = this

        ConfigLoader.readConfig(this)
        sqlSession = SqlFactory.initDB()

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