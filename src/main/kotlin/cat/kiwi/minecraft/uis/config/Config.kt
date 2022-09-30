package cat.kiwi.minecraft.uis.config

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin

object Config {
    lateinit var tablePrefix:String
    fun readConfig(instance: UltimateInventoryShopPlugin) {
        instance.saveConfig()
        tablePrefix = instance.config.getString("dataSource.prefix")!!
    }
}