package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.consts.indexDescriptionTable
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

val Inventory.uisIndex: Int
    get() {
        return this.getUisIndex()
    }

val Inventory.status: String?
    get() {
        return this.getItem(0)!!.getUisCondition()
    }

val Inventory.indexItemStack: ItemStack
    get() {
        return this.getItem(7)!!
    }

fun Inventory.updateIndexItemStack(index: Int) {
    println("update index $index")
    this.setItem(7, this.indexItemStack.setUisIndex(index))
}

fun Inventory.fillTable() {
    val goodsService = UltimateInventoryShopPlugin.goodsService


    when (this.status) {
        "allGoods" -> {
            val goods = goodsService.getGoodsByIndex(this.uisIndex, false).list
            goods.forEachIndexed { i, good ->
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
                this.setShopItem(i, itemStack.setItemUid(good.id))
            }
            // padding
            (goods.size..40).forEach {
                this.setShopItem(it, null)
            }
        }
    }

}

fun Inventory.setShopItem(index: Int, itemStack: ItemStack?) {
    if (index < 0 || index > 39) {
        return
    }
    this.setItem(indexDescriptionTable[index], itemStack)
}
