package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.config.UISMaterial
import cat.kiwi.minecraft.uis.model.enum.ShopStatus
import cat.kiwi.minecraft.uis.model.enum.UisButton
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
            UisButton.PREVIOUS_PAGE, Lang.previousPage
        )
    private val nextPageItemStack: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.nextPageMaterial), 1).setUisCondition(UisButton.NEXT_PAGE, Lang.nextPage)

    private val statusBlock: ItemStack = ItemStack(Material.PLAYER_HEAD, 1)
    private val currentGoods: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.currentGoods), 1).setUisCondition(UisButton.ALL_GOODS, Lang.currentGoods)
    private val myGoods: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.myGoods), 1).setUisCondition(UisButton.MY_GOODS, Lang.myGoods)
    private val myGoodsBeenSold: ItemStack =
        ItemStack(Material.valueOf(UISMaterial.myGoodsBeenSold), 1).setUisCondition(
            UisButton.MY_GOODS_BEEN_SOLD, Lang.myGoodsBeenSold
        )

    fun initGeneralStructure(player: Player) {
        inventory =
            createInventory(/* owner = */ player, /* size = */ 54, /* title = */ "${Lang.uisTitle} ${Lang.currentGoods}")

        inventory.setItem(0, statusBlock)
        inventory.setUisPlayerHead(player)
        inventory.uisIdentity = true
        inventory.uisStatus = ShopStatus.ALL_GOODS

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
            createInventory(/* owner = */ player, /* size = */ 54, /* title = */ Lang.uisTitle)

        inventory.setItem(0, statusBlock)
        inventory.setUisPlayerHead(player)
        inventory.uisIdentity = true
        inventory.uisStatus = ShopStatus.SPECIFIED_PLAYER


        inventory.setItem(6, previousPageItemStack)
        inventory.setItem(7, pageIndexItemStack)
        inventory.setItem(8, nextPageItemStack)

        inventory.uisIndex = 1

        player.openInventory(inventory)

        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            val tPlayerUID = UltimateInventoryShopPlugin.playerService.getPlayerUUIDByName(targetPlayer)
            if (tPlayerUID == null) {
                UisLogger.debug("Player $targetPlayer not found", this::class.java)
                inventory.setItem(0, statusBlock.setDisplayName("${Lang.playerNotFound} $targetPlayer"))
                inventory.uisIdentity = true
                player.sendMessage("${Lang.prefix} ${Lang.playerNotFound}")
                return@Runnable
            }

            inventory.uisTargetPlayerName = targetPlayer
            inventory.uisTargetPlayerUUID = tPlayerUID
            inventory.fillTable()
        })
    }
}
