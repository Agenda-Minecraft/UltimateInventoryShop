package cat.kiwi.minecraft.uis.config

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin

object Config {
    lateinit var tablePrefix: String
    var debug: Boolean = false

    var enableTax: Boolean = false
    var taxRate: Double = 0.0
    lateinit var taxAccountUUID: String

    private val logger = UltimateInventoryShopPlugin.instance.logger

    fun readConfig(instance: UltimateInventoryShopPlugin) {
        instance.saveDefaultConfig()
        try {
            debug = instance.config.getBoolean("debug")
            tablePrefix = instance.config.getString("dataSource.prefix")!!
            enableTax = instance.config.getBoolean("enableTax")
            taxAccountUUID = instance.config.getString("taxAccountUUID")!!
            taxRate = instance.config.getDouble("taxRate")
            Lang.prefix = instance.config.getString("lang.prefix")!!
            Lang.uisTitle = instance.config.getString("lang.uisTitle")!!
            Lang.currentGoods = instance.config.getString("lang.currentGoods")!!
            Lang.myGoods = instance.config.getString("lang.myGoods")!!
            Lang.myGoodsBeenSold = instance.config.getString("lang.myGoodsBeenSold")!!
            Lang.pageIndexName = instance.config.getString("lang.pageIndexName")!!
            Lang.previousPage = instance.config.getString("lang.previousPage")!!
            Lang.nextPage = instance.config.getString("lang.nextPage")!!
            Lang.emptyInMainHand = instance.config.getString("lang.emptyInMainHand")!!
            Lang.sellUsage = instance.config.getString("lang.sellUsage")!!
            Lang.materialLoadError = instance.config.getString("lang.materialLoadError")!!
            Lang.price = instance.config.getString("lang.price")!!
            Lang.sellSuc = instance.config.getString("lang.sellSuc")!!
            Lang.sellFail = instance.config.getString("lang.sellFail")!!
            Lang.sqlError = instance.config.getString("lang.sqlError")!!
            Lang.seller = instance.config.getString("lang.seller")!!
            Lang.buyer = instance.config.getString("lang.buyer")!!
            Lang.playerNotFound = instance.config.getString("lang.playerNotFound")!!
            Lang.buyFail = instance.config.getString("lang.buyFail")!!
            Lang.invalidItem = instance.config.getString("lang.invalidItem")!!
            Lang.buySuc = instance.config.getString("lang.buySuc")!!
            Lang.noEnoughMoney = instance.config.getString("lang.noEnoughMoney")!!
            Lang.invFull = instance.config.getString("lang.invFull")!!
            Lang.taxAccountError = instance.config.getString("lang.taxAccountError")!!
            Lang.afterTax = instance.config.getString("lang.afterTax")!!
            Lang.redeemSuc = instance.config.getString("lang.redeemSuc")!!
            Lang.redeemFail = instance.config.getString("lang.redeemFail")!!
            Lang.cannotBuySelfGoods = instance.config.getString("lang.cannotBuySelfGoods")!!
            Lang.notBelongToYou = instance.config.getString("lang.notBelongToYou")!!
            Lang.sellPriceError = instance.config.getString("lang.sellPriceError")!!
            Lang.helpMessage = instance.config.getList("lang.helpMessage")!!.joinToString("\n")
            Lang.noPermission = instance.config.getString("lang.noPermission")!!
            Lang.reloadLang = instance.config.getString("lang.reloadLang")!!
            Lang.taxRateInfo = instance.config.getString("lang.taxRateInfo")!!
        } catch (e: Exception) {
            logger.warning("[UltimateInventoryShop]Config file is not correct!")
            logger.warning("[UltimateInventoryShop]配置文件读取失败")
            UltimateInventoryShopPlugin.instance.onDisable()
        }
        try {
            UISMaterial.pageIndexMaterial = instance.config.getString("material.pageIndexMaterial")!!
            UISMaterial.previousPageMaterial = instance.config.getString("material.previousPageMaterial")!!
            UISMaterial.nextPageMaterial = instance.config.getString("material.nextPageMaterial")!!
            UISMaterial.myGoods = instance.config.getString("material.myGoods")!!
            UISMaterial.myGoodsBeenSold = instance.config.getString("material.myGoodsBeenSold")!!
            UISMaterial.currentGoods = instance.config.getString("material.currentGoods")!!
        } catch (e: Exception) {
            logger.warning("${Lang.prefix}${Lang.materialLoadError}")
        }
    }
}