package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.consts.indexTable
import cat.kiwi.minecraft.uis.controller.ShopInventory.Companion.nextPageItemStack
import cat.kiwi.minecraft.uis.controller.ShopInventory.Companion.previousPageItemStack
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

        when (e.currentItem!!.itemMeta!!.displayName) {
            previousPageItemStack!!.itemMeta!!.displayName -> {
                println("pre")
            }

            nextPageItemStack!!.itemMeta!!.displayName -> {
                println("next")
            }
        }
    }
}