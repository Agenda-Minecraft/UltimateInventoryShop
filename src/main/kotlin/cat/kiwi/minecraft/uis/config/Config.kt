package cat.kiwi.minecraft.uis.config

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin

object Config {
    lateinit var tablePrefix: String
    var debug: Boolean = false

    var enableTax: Boolean = false
    var taxRate: Double = 0.0
    var enablePapi = false
    var allowUnsafeEnchantments = true
    lateinit var taxAccountUUID: String

    private val logger = UltimateInventoryShopPlugin.instance.logger

    fun readConfig(instance: UltimateInventoryShopPlugin) {
        instance.saveDefaultConfig()
        val unFindConfig = mutableListOf<String>()
        try {
            debug = instance.config.getBoolean("debug")
            tablePrefix = instance.config.getString("dataSource.prefix")!!
            enableTax = instance.config.getBoolean("enableTax")
            taxAccountUUID = instance.config.getString("taxAccountUUID")!!
            taxRate = instance.config.getDouble("taxRate")
            enablePapi = instance.config.getBoolean("enablePapi")
            allowUnsafeEnchantments = instance.config.getBoolean("allowUnsafeEnchantments")
            Lang.prefix =
                instance.config.getString("lang.prefix") ?: "§6§l[全球商店] §f".also { unFindConfig.add("lang.prefix") }
            Lang.uisTitle =
                instance.config.getString("lang.uisTitle") ?: "§2全球商店".also { unFindConfig.add("lang.uisTitle") }
            Lang.currentGoods = instance.config.getString("lang.currentGoods")
                ?: "§9当前商品".also { unFindConfig.add("lang.currentGoods") }
            Lang.myGoods =
                instance.config.getString("lang.myGoods") ?: "§9我出售的".also { unFindConfig.add("lang.myGoods") }
            Lang.myGoodsBeenSold = instance.config.getString("lang.myGoodsBeenSold")
                ?: "§9我出售的(已售出)".also { unFindConfig.add("lang.myGoodsBeenSold") }
            Lang.pageIndexName = instance.config.getString("lang.pageIndexName")
                ?: "§9当前页码: §9".also { unFindConfig.add("lang.pageIndexName") }
            Lang.previousPage = instance.config.getString("lang.previousPage")
                ?: "§9上一页".also { unFindConfig.add("lang.previousPage") }
            Lang.nextPage =
                instance.config.getString("lang.nextPage") ?: "§9下一页".also { unFindConfig.add("lang.nextPage") }
            Lang.emptyInMainHand = instance.config.getString("lang.emptyInMainHand")
                ?: "§c手上没有东西".also { unFindConfig.add("lang.emptyInMainHand") }
            Lang.sellUsage = instance.config.getString("lang.sellUsage")
                ?: "§2/uis sell <价格> <描述>".also { unFindConfig.add("lang.sellUsage") }
            Lang.materialLoadError = instance.config.getString("lang.materialLoadError")
                ?: "§c物品加载失败".also { unFindConfig.add("lang.materialLoadError") }
            Lang.price = instance.config.getString("lang.price") ?: "§2价格 §6".also { unFindConfig.add("lang.price") }
            Lang.sellSuc =
                instance.config.getString("lang.sellSuc") ?: "§2出售成功".also { unFindConfig.add("lang.sellSuc") }
            Lang.sellFail =
                instance.config.getString("lang.sellFail") ?: "§c出售失败".also { unFindConfig.add("lang.sellFail") }
            Lang.sqlError =
                instance.config.getString("lang.sqlError") ?: "§c数据库错误".also { unFindConfig.add("lang.sqlError") }
            Lang.seller =
                instance.config.getString("lang.seller") ?: "§2卖家 §6".also { unFindConfig.add("lang.seller") }
            Lang.buyer = instance.config.getString("lang.buyer") ?: "§2买家 §6".also { unFindConfig.add("lang.buyer") }
            Lang.playerNotFound = instance.config.getString("lang.playerNotFound")
                ?: "§c未找到玩家".also { unFindConfig.add("lang.playerNotFound") }
            Lang.buyFail = instance.config.getString("lang.buyFail")
                ?: "§c购买失败，商品可能已被他人购买".also { unFindConfig.add("lang.buyFail") }
            Lang.invalidItem = instance.config.getString("lang.invalidItem")
                ?: "§c无效的物品".also { unFindConfig.add("lang.invalidItem") }
            Lang.buySuc =
                instance.config.getString("lang.buySuc") ?: "§2购买成功".also { unFindConfig.add("lang.buySuc") }
            Lang.noEnoughMoney = instance.config.getString("lang.noEnoughMoney")
                ?: "§c你的余额不足".also { unFindConfig.add("lang.noEnoughMoney") }
            Lang.invFull =
                instance.config.getString("lang.invFull") ?: "§c你的背包已满".also { unFindConfig.add("lang.invFull") }
            Lang.taxAccountError = instance.config.getString("lang.taxAccountError")
                ?: "§c税收账户错误".also { unFindConfig.add("lang.taxAccountError") }
            Lang.afterTax =
                instance.config.getString("lang.afterTax") ?: "§2税后价格 §6".also { unFindConfig.add("lang.afterTax") }
            Lang.redeemSuc =
                instance.config.getString("lang.redeemSuc") ?: "§2兑换成功".also { unFindConfig.add("lang.redeemSuc") }
            Lang.redeemFail = instance.config.getString("lang.redeemFail")
                ?: "§c兑换失败".also { unFindConfig.add("lang.redeemFail") }
            Lang.cannotBuySelfGoods = instance.config.getString("lang.cannotBuySelfGoods")
                ?: "§c你不能购买自己的商品".also { unFindConfig.add("lang.cannotBuySelfGoods") }
            Lang.notBelongToYou = instance.config.getString("lang.notBelongToYou")
                ?: "§c这不是你的商品".also { unFindConfig.add("lang.notBelongToYou") }
            Lang.sellPriceError = instance.config.getString("lang.sellPriceError")
                ?: "§c出售价格错误".also { unFindConfig.add("lang.sellPriceError") }
            Lang.helpMessage =
                (instance.config.getList("lang.helpMessage") ?: arrayListOf("无帮助信息")).joinToString("\n")
            Lang.noPermission = instance.config.getString("lang.noPermission")
                ?: "§c你没有权限".also { unFindConfig.add("lang.noPermission") }
            Lang.reloadLang = instance.config.getString("lang.reloadLang")
                ?: "§2重载语言文件成功".also { unFindConfig.add("lang.reloadLang") }
            Lang.taxRateInfo = instance.config.getString("lang.taxRateInfo")
                ?: "§2税率 §6".also { unFindConfig.add("lang.taxRateInfo") }
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("[UltimateInventoryShop]Config file is not correct!")
            logger.warning("[UltimateInventoryShop]配置文件读取失败")
            UltimateInventoryShopPlugin.instance.onDisable()
        }
        try {
            UISMaterial.pageIndexMaterial = instance.config.getString("material.pageIndexMaterial")
                ?: "PAPER".also { unFindConfig.add("material.pageIndexMaterial") }
            UISMaterial.previousPageMaterial = instance.config.getString("material.previousPageMaterial")?: "BIRCH_BUTTON".also { unFindConfig.add("material.previousPageMaterial") }
            UISMaterial.nextPageMaterial = instance.config.getString("material.nextPageMaterial")?: "BIRCH_BUTTON".also { unFindConfig.add("material.nextPageMaterial") }
            UISMaterial.myGoods = instance.config.getString("material.myGoods")?: "GRASS_BLOCK".also { unFindConfig.add("material.myGoods") }
            UISMaterial.myGoodsBeenSold = instance.config.getString("material.myGoodsBeenSold")?: "GOLD_INGOT".also { unFindConfig.add("material.myGoodsBeenSold") }
            UISMaterial.currentGoods = instance.config.getString("material.currentGoods")?: "DIAMOND".also { unFindConfig.add("material.currentGoods") }
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("${Lang.prefix}${Lang.materialLoadError}")
            if (unFindConfig.isNotEmpty()) {
                instance.logger.warning("未找到以下配置项(Unable to find these config): ${unFindConfig.joinToString(", ")}")
            }
        }
    }
}