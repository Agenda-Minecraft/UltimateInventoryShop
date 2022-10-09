package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.config.UISMaterial
import cat.kiwi.minecraft.uis.utils.*
import org.bukkit.Bukkit
import org.bukkit.Bukkit.createInventory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


class InventoryBaseStructureController {
    lateinit var inventory: Inventory
    private var pageIndexItemStack: ItemStack = ItemStack(Material.valueOf(UISMaterial.pageIndexMaterial), 1)

    private val previousPageItemStack: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.previousPageMaterial), 1).setUisCondition(
            "previousPage", Lang.previousPage
        )
    private val nextPageItemStack: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.nextPageMaterial), 1).setUisCondition("nextPage", Lang.nextPage)

    private val statusBlock: ItemStack = ItemStack(Material.PLAYER_HEAD, 1)
    private val currentGoods: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.currentGoods), 1).setUisCondition("allGoods", Lang.currentGoods)
    private val myGoods: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.myGoods), 1).setUisCondition("myGoods", Lang.myGoods)
    private val myGoodsBeenSold: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.myGoodsBeenSold), 1).setUisCondition(
            "myGoodsBeenSold", Lang.myGoodsBeenSold
        )

    fun initGeneralStructure(player: Player) {
        inventory =
            createInventory(/* owner = */ player, /* size = */ 54, /* title = */ "${Lang.uisName} ${Lang.currentGoods}")

        inventory.setItem(0, statusBlock)
        inventory.setUisPlayerHead(player)
        inventory.uisIdentity = true
        inventory.uisStatus = "allGoods"

        inventory.setItem(18, currentGoods)
        inventory.setItem(27, myGoods)
        inventory.setItem(36, myGoodsBeenSold)

        inventory.setItem(6, previousPageItemStack)
        inventory.setItem(7, pageIndexItemStack)
        inventory.setItem(8, nextPageItemStack)

        inventory.uisIndex = 1

        player.openInventory(inventory)

        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            inventory.fillTable()
        })
    }
    fun initSpecifyStructure(player: Player, targetPlayer: String) {
        inventory =
            createInventory(/* owner = */ player, /* size = */ 54, /* title = */ "${Lang.uisName} ${Lang.currentGoods}")

        inventory.setItem(0, statusBlock)
        // get player from name
        inventory.setUisPlayerHead(player)
        inventory.uisIdentity = true
        inventory.uisStatus = "specifyPlayer"

        inventory.setItem(6, previousPageItemStack)
        inventory.setItem(7, pageIndexItemStack)
        inventory.setItem(8, nextPageItemStack)

        inventory.uisIndex = 1

        player.openInventory(inventory)

        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            inventory.fillTable()
        })
    }
}
