package cat.kiwi.minecraft.uis


import cat.kiwi.minecraft.uis.command.UisCommands
import cat.kiwi.minecraft.uis.command.UisErrorCommand
import cat.kiwi.minecraft.uis.config.Config
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.service.GoodsService
import cat.kiwi.minecraft.uis.service.InitDBService
import cat.kiwi.minecraft.uis.service.PlayerService
import cat.kiwi.minecraft.uis.service.impl.GoodsServiceImpl
import cat.kiwi.minecraft.uis.service.impl.InitDBServiceImpl
import cat.kiwi.minecraft.uis.service.impl.PlayerServiceImpl
import cat.kiwi.minecraft.uis.utils.SqlFactory
import net.milkbowl.vault.economy.Economy
import org.apache.ibatis.session.SqlSession
import org.bukkit.plugin.RegisteredServiceProvider
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable


class UltimateInventoryShopPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: UltimateInventoryShopPlugin
        lateinit var sqlSession: SqlSession
        lateinit var initDBService: InitDBService
        lateinit var goodsService: GoodsService
        lateinit var playerService: PlayerService
        lateinit var economy: Economy
    }

    private var registerCounter = 0
    override fun onEnable() {
        if (!setupEconomy()) {
            logger.info("Disabled due to no Vault dependency found!")
            logger.info("Vault 插件未找到!")
            onDisable()
            return
        }
        instance = this

        Config.readConfig(this)
        try {
            sqlSession = SqlFactory.initDB()
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning(Lang.sqlError)
            onDisable()
            return
        }

        initDBService = InitDBServiceImpl()
        goodsService = GoodsServiceImpl()
        playerService = PlayerServiceImpl()

        getCommand("uis")?.setExecutor(UisCommands())

        UltimateInventoryShop(this)
        tryRegisterProvider()
        logger.info("UltimateInventoryShop has been enabled!")

    }

    private fun tryRegisterProvider() {
        object : BukkitRunnable() {
            override fun run() {
                logger.info("Try to register provider... attempt $registerCounter")
                val rsp: RegisteredServiceProvider<Economy>? =
                    server.servicesManager.getRegistration(Economy::class.java)
                registerCounter++
                if (rsp != null) {
                    cancel()
                    economy = rsp.provider
                    logger.info("Provider register succeed: ${rsp.plugin.name}")
                }
                if (registerCounter > 50) {
                    logger.info("Failed to register provider!")
                    cancel()
                    instance.onDisable()
                }
            }
        }.runTaskTimerAsynchronously(this, 20, 100)
    }

    private fun setupEconomy(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }

        return true
    }

    override fun onDisable() {
        getCommand("uis")?.setExecutor(UisErrorCommand())
        try {
            sqlSession.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        logger.info("UltimateInventoryShop has been disabled!")
    }
}