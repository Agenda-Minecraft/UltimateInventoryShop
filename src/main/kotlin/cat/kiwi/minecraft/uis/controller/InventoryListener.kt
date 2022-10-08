package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.utils.fillTable
import cat.kiwi.minecraft.uis.utils.getUisCondition
import cat.kiwi.minecraft.uis.utils.getUisIndex
import cat.kiwi.minecraft.uis.utils.setUisIndex
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (e.view.title != Lang.uisName) {
            return
        }
        if (e.currentItem!!.type == Material.AIR) {
            return
        }
        e.isCancelled = true

        when (e.currentItem!!.getUisCondition()) {
            "previousPage" -> {
                var index = e.currentItem!!.getUisIndex()
                if (index <= 1) {
                    return
                }
                index--
                e.inventory.setItem(7, e.currentItem!!.setUisIndex(index))
                e.inventory.fillTable()
            }

            "nextPage" -> {
                e.isCancelled = true
                var index = e.currentItem!!.getUisIndex()
                if (index >= UltimateInventoryShopPlugin.goodsService.getGoodsByIndex(index).pageNum) {
                    return
                }
                index++
                e.inventory.setItem(7, e.currentItem!!.setUisIndex(index))
            }
        }
    }
}