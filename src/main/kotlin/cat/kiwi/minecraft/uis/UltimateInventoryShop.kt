package cat.kiwi.minecraft.uis

import cat.kiwi.minecraft.uis.controller.InventoryListener
import cat.kiwi.minecraft.uis.controller.ShopInventory
import cat.kiwi.minecraft.uis.service.InitDBService
import cat.kiwi.minecraft.uis.service.impl.InitDBServiceImpl

class UltimateInventoryShop(val instance: UltimateInventoryShopPlugin) {
    private val initDbService: InitDBService = InitDBServiceImpl()
    init {
        initDbService.createTableIfNotExist()

        instance.server.pluginManager.registerEvents(InventoryListener(), instance)
    }

}