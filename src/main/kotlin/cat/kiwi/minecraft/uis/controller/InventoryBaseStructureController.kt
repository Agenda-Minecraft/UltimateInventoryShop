package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.config.UISMaterial
import cat.kiwi.minecraft.uis.utils.fillTable
import cat.kiwi.minecraft.uis.utils.setUisCondition
import cat.kiwi.minecraft.uis.utils.setUisIndex
import org.bukkit.Bukkit.createInventory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


class InventoryBaseStructureController {
    lateinit var inventory: Inventory
    private var pageIndexItemStack: ItemStack = ItemStack(Material.valueOf(UISMaterial.pageIndexMaterial), 1).setUisIndex(0)

    companion object {
        val previousPageItemStack: ItemStack =
            ItemStack(Material.valueOf(UISMaterial.previousPageMaterial), 1).setUisCondition(
                "previous",
                Lang.previousPage
            )
        val nextPageItemStack: ItemStack =
            ItemStack(Material.valueOf(UISMaterial.nextPageMaterial), 1).setUisCondition("next", Lang.nextPage)
    }

    fun initStructure(player: Player) {
        inventory = createInventory(/* owner = */ player, /* size = */ 54, /* title = */ Lang.uisName)

        inventory.setItem(7, pageIndexItemStack)
        inventory.setItem(6, previousPageItemStack)
        inventory.setItem(8, nextPageItemStack)

        player.openInventory(inventory)
        inventory.fillTable()
    }
}
