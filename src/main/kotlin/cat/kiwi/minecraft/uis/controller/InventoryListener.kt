package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.config.UISMaterial
import cat.kiwi.minecraft.uis.model.entity.ShopType
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class InventoryListener(player: Player) : Listener {
    val inventory: Inventory = Bukkit.createInventory(player, 54, Lang.uisName)

    private var sellType = ShopType.MARKET


    private var beenSold: Boolean = false
    private var index: Int = 0

    private var pageIndexItemStack: ItemStack = ItemStack(Material.valueOf(UISMaterial.pageIndexMaterial), 1)


    private val previousPageItemStack: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.previousPageMaterial), 1).also {
            it.itemMeta!!.setDisplayName(Lang.previousPage)
        }
    private val nextPageItemStack: ItemStack = ItemStack(Material.valueOf(UISMaterial.nextPageMaterial), 1).also {
        it.itemMeta!!.setDisplayName(Lang.nextPage)
    }


    init {
        pageIndexItemStack.itemMeta = pageIndexItemStack.itemMeta.apply {
            this!!.setDisplayName("${Lang.pageIndexName} $index")
        }

        inventory.setItem(7, pageIndexItemStack)
        inventory.setItem(6, previousPageItemStack)
        inventory.setItem(8, nextPageItemStack)
    }


}