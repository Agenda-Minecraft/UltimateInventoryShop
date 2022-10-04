package cat.kiwi.minecraft.uis.config

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin

object ConfigLoader {
    lateinit var tablePrefix: String
    fun readConfig(instance: UltimateInventoryShopPlugin) {
        instance.saveConfig()
        tablePrefix = instance.config.getString("dataSource.prefix")!!
        Lang.uisName = instance.config.getString("lang.uisName")!!
        Lang.currentGoods = instance.config.getString("lang.currentGoods")!!
        Lang.myGoods = instance.config.getString("lang.myGoods")!!
        Lang.myGoodsBeenSold = instance.config.getString("lang.myGoodsBeenSold")!!
        Lang.pageIndexName = instance.config.getString("lang.pageIndexName")!!
        Lang.previousPage = instance.config.getString("lang.previousPage")!!
        Lang.nextPage = instance.config.getString("lang.nextPage")!!


        UISMaterial.pageIndexMaterial = instance.config.getString("material.pageIndexMaterial")!!
        UISMaterial.previousPageMaterial = instance.config.getString("material.previousPageMaterial")!!
        UISMaterial.nextPageMaterial = instance.config.getString("material.nextPageMaterial")!!
    }
}