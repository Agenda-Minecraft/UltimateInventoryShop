package cat.kiwi.minecraft.uis.model.entity

import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.utils.b64Deserialized
import cat.kiwi.minecraft.uis.utils.setItemUid
import cat.kiwi.minecraft.uis.utils.setUisCondition
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*

data class GoodPojo(
    val id: String,
    val putterName: String,
    val putterUid: String,
    val callerName: String,
    val callerUid: String,
    val price: Double,
    val itemInfo: String,
    val itemTag: String,
    val description: String,
    val beenSold: Boolean,
    val createDate: Date,
    val dealDate: Date,
    val meta: String
)

val GoodPojo.renderedGoods: ItemStack
    get() {
        var itemStack = this.itemInfo.b64Deserialized
        val itemMeta: ItemMeta = itemStack.itemMeta!!
        val enhance = itemMeta.enchants
        itemMeta.setDisplayName(itemMeta.displayName)
        if (itemMeta.lore == null) {
            val loreList: MutableList<String> = mutableListOf()
            loreList.add("${Lang.price} ${this.price}")
            loreList.add(this.description)
            itemMeta.lore = loreList
        } else {
            itemMeta.lore!!.add("${Lang.price} ${this.price}")
            itemMeta.lore!!.add(this.description)
        }
        itemStack.itemMeta = itemMeta
        if (enhance.isNotEmpty()) {
            itemStack.addEnchantments(enhance)
        }
        itemStack = itemStack.setUisCondition("goodsItem")
        return itemStack.setItemUid(this.id)
    }
