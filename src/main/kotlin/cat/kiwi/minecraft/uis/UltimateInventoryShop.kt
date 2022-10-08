package cat.kiwi.minecraft.uis

import cat.kiwi.minecraft.uis.controller.InventoryListener
import cat.kiwi.minecraft.uis.service.InitDBService
import de.tr7zw.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class UltimateInventoryShop(private val instance: UltimateInventoryShopPlugin) {
    private val initDbService: InitDBService = UltimateInventoryShopPlugin.initDBService
    init {
        initDbService.createTableIfNotExist()
        instance.server.pluginManager.registerEvents(InventoryListener(), instance)
    }

}