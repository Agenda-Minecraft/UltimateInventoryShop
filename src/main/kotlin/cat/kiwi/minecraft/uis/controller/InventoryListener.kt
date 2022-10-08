package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.utils.fillTable
import cat.kiwi.minecraft.uis.utils.getUisCondition
import cat.kiwi.minecraft.uis.utils.getUisIndex
import cat.kiwi.minecraft.uis.utils.updateIndexItemStack
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (e.view.title != Lang.uisName) return
        if (e.currentItem == null) return
        if (e.currentItem!!.type == Material.AIR) return

        e.isCancelled = true

        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            val condition = e.currentItem!!.getUisCondition()
            UltimateInventoryShopPlugin.instance.logger.warning(condition)
            when (condition) {
                "previousPage" -> {
                    var index = e.inventory.getUisIndex()
                    if (index <= 1) {
                        return@Runnable
                    }
                    index--
                    e.inventory.updateIndexItemStack(index)
                    e.inventory.fillTable()
                }

                "nextPage" -> {
                    var index = e.inventory.getUisIndex()
                    println("current index $index")
                    if (index >= UltimateInventoryShopPlugin.goodsService.getPageNum(false).also { println(it) }) {
                        return@Runnable
                    }
                    index++
                    e.inventory.updateIndexItemStack(index)
                    e.inventory.fillTable()
                }
            }
        })
    }
}