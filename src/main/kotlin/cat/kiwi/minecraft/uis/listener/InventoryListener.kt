package cat.kiwi.minecraft.uis.listener

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.utils.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (e.currentItem == null) return
        if (e.currentItem!!.type == Material.AIR) return

        if (!e.inventory.uisIdentity) return

        e.isCancelled = true

        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            val condition = e.currentItem!!.getUisCondition()
            UISLogger.debug("condition: $condition")
            UISLogger.debug("status : ${e.inventory.uisStatus}")
            when (condition) {
                "previousPage" -> {
                    var index = e.inventory.uisIndex
                    if (index <= 1) {
                        return@Runnable
                    }
                    index--
                    e.inventory.uisIndex = index
                    e.inventory.fillTable()
                }

                "nextPage" -> {
                    var index = e.inventory.uisIndex
                    if (index >= UltimateInventoryShopPlugin.goodsService.getPageNum(false)) {
                        return@Runnable
                    }
                    index++
                    e.inventory.uisIndex = index
                    e.inventory.fillTable()
                }

                "allGoods" -> {
                    e.inventory.resetStatus(condition)
                }

                "myGoods" -> {
                    e.inventory.resetStatus(condition)
                }
                "myGoodsBeenSold" -> {
                    e.inventory.resetStatus(condition)
                }
                "goodsItem" -> {
                    UltimateInventoryShopPlugin.instance.logger.info(e.currentItem.toString())
                }
            }
        })
    }
}