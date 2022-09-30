package cat.kiwi.minecraft.uis


import cat.kiwi.minecraft.uis.config.Config
import cat.kiwi.minecraft.uis.utils.SqlFactory
import org.apache.ibatis.session.SqlSession
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class UltimateInventoryShopPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: UltimateInventoryShopPlugin
        lateinit var sqlSession: SqlSession
    }

    override fun onEnable() {
        instance = this

        Config.readConfig(this)
        sqlSession = SqlFactory.initDB()
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