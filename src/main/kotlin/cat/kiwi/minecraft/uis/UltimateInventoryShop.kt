package cat.kiwi.minecraft.uis

import cat.kiwi.minecraft.uis.listener.InventoryListener
import cat.kiwi.minecraft.uis.listener.RecordListener
import cat.kiwi.minecraft.uis.service.InitDBService

class UltimateInventoryShop(private val instance: UltimateInventoryShopPlugin) {
    private val initDbService: InitDBService = UltimateInventoryShopPlugin.initDBService
    init {
        initDbService.createTableIfNotExist()
        instance.server.pluginManager.registerEvents(InventoryListener(), instance)
        instance.server.pluginManager.registerEvents(RecordListener(), instance)
    }
}