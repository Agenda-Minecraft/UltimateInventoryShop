package cat.kiwi.minecraft.uis

import cat.kiwi.minecraft.uis.service.InitDBService
import cat.kiwi.minecraft.uis.service.impl.InitDBServiceImpl

class UltimateInventoryShop(val instance: UltimateInventoryShopPlugin) {
    private val initDbService: InitDBService = InitDBServiceImpl()
    init {
        initDbService.createTableIfNotExist()
    }

}