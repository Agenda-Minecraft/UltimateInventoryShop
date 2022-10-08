package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.utils.getUisCondition
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
            }

            "nextPage" -> {
                e.isCancelled = true
            }
        }
    }
}