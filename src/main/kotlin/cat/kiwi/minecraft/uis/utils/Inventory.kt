package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.consts.indexDescriptionTable
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

val Inventory.uisIndex: Int
    get() {
        this.getItem(7)
        return 0
    }
val Inventory.beenSold: Boolean
    get() {
        return true
    }


fun Inventory.fillTable() {
    val goodsService = UltimateInventoryShopPlugin.goodsService

    Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
        goodsService.getGoodsByIndex(this.uisIndex, this.beenSold).list.forEachIndexed { i, good ->
            val itemStack = good.itemInfo.b64Deserialized
            val itemMeta: ItemMeta = itemStack.itemMeta!!
            val enhance = itemMeta.enchants
            itemMeta.setDisplayName(itemMeta.displayName)
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
            this.setShopItem(i, itemStack)
        }
    })

}

fun Inventory.setShopItem(index: Int, itemStack: ItemStack) {
    if (index < 0 || index > 39) {
        return
    }
    this.setItem(indexDescriptionTable[index], itemStack)
}
