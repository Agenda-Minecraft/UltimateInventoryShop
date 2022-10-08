package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.config.UISMaterial
import cat.kiwi.minecraft.uis.consts.indexTable
import cat.kiwi.minecraft.uis.model.entity.ShopType
import cat.kiwi.minecraft.uis.service.GoodsService
import cat.kiwi.minecraft.uis.service.impl.GoodsServiceImpl
import cat.kiwi.minecraft.uis.utils.b64Deserialized
import org.bukkit.Bukkit
import org.bukkit.Bukkit.createInventory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ShopInventory() {
    private val goodsService: GoodsService = GoodsServiceImpl()
    lateinit var inventory: Inventory


    private var sellType = ShopType.MARKET


    private var beenSold: Boolean = false
    private var index: Int = 0

    private var pageIndexItemStack: ItemStack = ItemStack(Material.valueOf(UISMaterial.pageIndexMaterial), 1).also {
        it.itemMeta = it.itemMeta.also { meta ->
            meta!!.setDisplayName("${Lang.pageIndexName} $index")
        }
    }

    companion object {
        val previousPageItemStack: ItemStack =
            ItemStack(Material.valueOf(UISMaterial.previousPageMaterial), 1).also {
                it.itemMeta = it.itemMeta.also { meta ->
                    meta!!.setDisplayName(Lang.previousPage)
                }
            }
        val nextPageItemStack: ItemStack = ItemStack(Material.valueOf(UISMaterial.nextPageMaterial), 1).also {
            it.itemMeta = it.itemMeta.also { meta ->
                meta!!.setDisplayName(Lang.nextPage)
            }
        }

    }


    fun openInventory(player: Player) {
        inventory = createInventory(/* owner = */ player, /* size = */ 54, /* title = */ Lang.uisName)

        inventory.setItem(7, pageIndexItemStack)
        inventory.setItem(6, previousPageItemStack)
        inventory.setItem(8, nextPageItemStack)

        player.openInventory(inventory)
        fillTable()
    }


    private fun Inventory.setShopItem(index: Int, itemStack: ItemStack) {
        if (index < 0 || index > 39) {
            return
        }
        this.setItem(indexTable[index], itemStack)
    }

    private fun fillTable() {
        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            goodsService.getGoodsByIndex(index, beenSold).list.forEachIndexed { i, good ->
                val itemStack = good.itemInfo.b64Deserialized
                val itemMeta: ItemMeta = itemStack.itemMeta!!
                val enhance = itemMeta.enchants
                itemMeta.setDisplayName(itemMeta!!.displayName)
                if (itemMeta.lore == null) {
                    val loreList: MutableList<String> = mutableListOf()
                    loreList.add("${Lang.price} ${good.price}")
                    loreList.add(good.description)
                    itemMeta.lore = loreList
                } else {
                    itemMeta.lore!!.add("${Lang.price} ${good.price}")
                    itemMeta.lore!!.add(good.description)
                }
                itemStack.itemMeta = itemMeta
                if (enhance.isNotEmpty()) {
                    itemStack.addEnchantments(enhance)
                }
                inventory.setShopItem(i, itemStack)
            }
        })
    }


}