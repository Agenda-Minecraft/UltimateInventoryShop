package cat.kiwi.minecraft.uis.config

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin

object ConfigLoader {
    lateinit var tablePrefix: String
    private val logger = UltimateInventoryShopPlugin.instance.logger
    fun readConfig(instance: UltimateInventoryShopPlugin) {
        instance.saveConfig()
        try {
            tablePrefix = instance.config.getString("dataSource.prefix")!!
            Lang.prefix = instance.config.getString("lang.prefix")!!
            Lang.uisName = instance.config.getString("lang.uisName")!!
            Lang.currentGoods = instance.config.getString("lang.currentGoods")!!
            Lang.myGoods = instance.config.getString("lang.myGoods")!!
            Lang.myGoodsBeenSold = instance.config.getString("lang.myGoodsBeenSold")!!
            Lang.pageIndexName = instance.config.getString("lang.pageIndexName")!!
            Lang.previousPage = instance.config.getString("lang.previousPage")!!
            Lang.nextPage = instance.config.getString("lang.nextPage")!!
            Lang.emptyInMainHand = instance.config.getString("lang.emptyInMainHand")!!
            Lang.sellUsage = instance.config.getString("lang.sellUsage")!!
            Lang.materialLoadError = instance.config.getString("lang.materialLoadError")!!
        } catch (e: Exception) {
            logger.warning("[UltimateInventoryShop]Config file is not correct!")
            logger.warning("[UltimateInventoryShop]配置文件读取失败")

        }
        try {
            UISMaterial.pageIndexMaterial = instance.config.getString("material.pageIndexMaterial")!!
            UISMaterial.previousPageMaterial = instance.config.getString("material.previousPageMaterial")!!
            UISMaterial.nextPageMaterial = instance.config.getString("material.nextPageMaterial")!!
        } catch (e: Exception) {
            logger.warning("${Lang.prefix}${Lang.materialLoadError}")
        }

    }
}