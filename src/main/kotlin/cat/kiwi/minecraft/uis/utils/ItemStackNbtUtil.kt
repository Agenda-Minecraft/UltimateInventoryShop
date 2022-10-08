package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.config.Lang
import de.tr7zw.nbtapi.NBTItem
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


fun ItemStack.setUisCondition(condition: String, displayName: String): ItemStack {
    this.itemMeta = this.itemMeta.also { meta ->
        meta!!.setDisplayName(displayName)
    }

    val nbtItem = NBTItem(this)
    var uisMetadata = nbtItem.getCompound("uisMeta")
    if (uisMetadata == null) {
        uisMetadata = nbtItem.addCompound("uisMeta")
    }
    uisMetadata.setString("condition", condition)
    return nbtItem.item
}

fun ItemStack.setUisCondition(condition: String): ItemStack {
    val nbtItem = NBTItem(this)
    var uisMetadata = nbtItem.getCompound("uisMeta")
    if (uisMetadata == null) {
        uisMetadata = nbtItem.addCompound("uisMeta")
    }
    uisMetadata.setString("condition", condition)
    return nbtItem.item
}

fun ItemStack.getUisCondition(): String? {
    val nbtItem = NBTItem(this)
    val uisMetadata = nbtItem.getCompound("uisMeta") ?: return null
    return uisMetadata.getString("condition")
}

fun ItemStack.setItemUid(): String? {
    val nbtItem = NBTItem(this)
    val uisMetadata = nbtItem.getCompound("uisMeta") ?: return null
    return uisMetadata.getString("uid")
}
fun ItemStack.setItemUid(uid: String): ItemStack {
    val nbtItem = NBTItem(this)
    var uisMetadata = nbtItem.getCompound("uisMeta")
    if (uisMetadata == null) {
        uisMetadata = nbtItem.addCompound("uisMeta")
    }
    uisMetadata.setString("uid", uid)
    return nbtItem.item
}



fun ItemStack.setUisIndex(index: Int): ItemStack {
    if (index > 64) {
        this.amount = 64
    } else {
        this.amount = index
    }
    this.itemMeta = this.itemMeta.also { meta ->
        meta!!.setDisplayName("${Lang.pageIndexName} $index")
    }

    val nbtItem = NBTItem(this)
    var uisMetadata = nbtItem.getCompound("uisMeta")
    if (uisMetadata == null) {
        uisMetadata = nbtItem.addCompound("uisMeta")
    }
    uisMetadata.setInteger("index", index)
    return nbtItem.item
}

fun Inventory.getUisIndex(): Int {
    val nbtItem = NBTItem(this.getItem(7))
    val uisMetadata = nbtItem.getCompound("uisMeta") ?: return 1
    return uisMetadata.getInteger("index") ?: return 1
}