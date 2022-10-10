package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.model.enum.ShopStatus
import cat.kiwi.minecraft.uis.model.enum.UisButton
import cat.kiwi.minecraft.uis.model.pojo.GoodPojo
import de.tr7zw.nbtapi.NBTItem
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*


fun ItemStack.setUisCondition(condition: UisButton, displayName: String): ItemStack {
    this.itemMeta = this.itemMeta.also { meta ->
        meta!!.setDisplayName(displayName)
    }

    val nbtItem = NBTItem(this)
    var uisMetadata = nbtItem.getCompound("uisMeta")
    if (uisMetadata == null) {
        uisMetadata = nbtItem.addCompound("uisMeta")
    }
    uisMetadata.setObject("condition", condition)
    return nbtItem.item
}

fun ItemStack.setUisCondition(condition: UisButton): ItemStack {
    val nbtItem = NBTItem(this)
    var uisMetadata = nbtItem.getCompound("uisMeta")
    if (uisMetadata == null) {
        uisMetadata = nbtItem.addCompound("uisMeta")
    }
    uisMetadata.setObject("condition", condition)
    return nbtItem.item
}

fun ItemStack.setUisItemMeta(goodPojo: GoodPojo): ItemStack {
    val nbtItem = NBTItem(this)
    var uisMetadata = nbtItem.getCompound("uisMeta")
    if (uisMetadata == null) {
        uisMetadata = nbtItem.addCompound("uisMeta")
    }
    uisMetadata.setString("id", goodPojo.id)
    uisMetadata.setString("callerName", goodPojo.callerName)
    uisMetadata.setString("callerUid", goodPojo.callerUid)
    uisMetadata.setString("putterName", goodPojo.putterName)
    uisMetadata.setString("putterUid", goodPojo.putterUid)
    return nbtItem.item
}

fun ItemStack.getUisItemMeta(key: String): String {
    val nbtItem = NBTItem(this)
    val uisMetadata = nbtItem.getCompound("uisMeta")
    return uisMetadata.getString(key)
}

fun ItemStack.getUisCondition(): UisButton? {
    val nbtItem = NBTItem(this)
    val uisMetadata = nbtItem.getCompound("uisMeta") ?: return null
    return uisMetadata.getObject("condition", UisButton::class.java)
}

var Inventory.uisTargetPlayerName: String
    get() {
        val nbtItem = NBTItem(this.getItem(0))
        val uisMetadata = nbtItem.getCompound("uisMeta") ?: return ""
        return uisMetadata.getString("targetPlayerName") ?: ""
    }
    set(value) {
        val nbtItem = NBTItem(this.getItem(0))
        var uisMetadata = nbtItem.getCompound("uisMeta")
        if (uisMetadata == null) {
            uisMetadata = nbtItem.addCompound("uisMeta")
        }
        uisMetadata.setString("targetPlayerName", value)
        this.setItem(0, nbtItem.item)
    }
var Inventory.uisTargetPlayerUUID: UUID
    get() {
        val nbtItem = NBTItem(this.getItem(0))
        val uisMetadata = nbtItem.getCompound("uisMeta") ?: return UUID.randomUUID()
        return uisMetadata.getUUID("targetPlayerUUID") ?: UUID.randomUUID()
    }
    set(value) {
        val nbtItem = NBTItem(this.getItem(0))
        var uisMetadata = nbtItem.getCompound("uisMeta")
        if (uisMetadata == null) {
            uisMetadata = nbtItem.addCompound("uisMeta")
        }
        uisMetadata.setUUID("targetPlayerUUID", value)
        this.setItem(0, nbtItem.item)
    }
var Inventory.uisIdentity: Boolean
    get() {
        val nbtItem = NBTItem(this.getItem(0))
        val uisMetadata = nbtItem.getCompound("uisMeta") ?: return false
        return uisMetadata.getBoolean("identity") ?: false
    }
    set(value) {
        val nbtItem = NBTItem(this.getItem(0))
        var uisMetadata = nbtItem.getCompound("uisMeta")
        if (uisMetadata == null) {
            uisMetadata = nbtItem.addCompound("uisMeta")
        }
        uisMetadata.setBoolean("identity", value)
        this.setItem(0, nbtItem.item)
    }

// allGoods myGoods myGoodsBeenSold specifiedPlayer
var Inventory.uisStatus: ShopStatus?
    set(value: ShopStatus?) {
        val itemStack = this.getItem(0)
        val nbtItem = NBTItem(itemStack)
        var uisMetadata = nbtItem.getCompound("uisMeta")
        if (uisMetadata == null) {
            uisMetadata = nbtItem.addCompound("uisMeta")
        }
        uisMetadata.setObject("status", value)
        this.setItem(0, nbtItem.item)
    }
    get() {
        val itemStack = this.getItem(0)
        val nbtItem = NBTItem(itemStack)
        var uisMetadata = nbtItem.getCompound("uisMeta")
        if (uisMetadata == null) {
            uisMetadata = nbtItem.addCompound("uisMeta")
        }
        return uisMetadata.getObject("status", ShopStatus::class.java)
    }

fun ItemStack.setDisplayName(displayName: String): ItemStack {
    this.itemMeta = this.itemMeta.also { meta ->
        meta!!.setDisplayName(displayName)
    }
    return this
}

fun Inventory.setUisPlayerHead(player: Player) {
    val itemStack = this.getItem(0)
    val skull = itemStack!!.itemMeta as SkullMeta
    skull.setDisplayName(player.name)
    skull.owningPlayer = player
    itemStack.itemMeta = skull
    this.setItem(0, itemStack)
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

var Inventory.uisIndex: Int
    get() {
        val nbtItem = NBTItem(this.getItem(7))
        val uisMetadata = nbtItem.getCompound("uisMeta") ?: return 1
        return uisMetadata.getInteger("index") ?: return 1
    }
    set(index) {
        val itemStack = this.getItem(7)
        if (index > 64) {
            itemStack!!.amount = 64
        } else {
            itemStack!!.amount = index
        }
        itemStack.itemMeta = itemStack.itemMeta.also { meta ->
            meta!!.setDisplayName("${Lang.pageIndexName}$index")
        }

        val nbtItem = NBTItem(itemStack)
        var uisMetadata = nbtItem.getCompound("uisMeta")
        if (uisMetadata == null) {
            uisMetadata = nbtItem.addCompound("uisMeta")
        }
        uisMetadata.setInteger("index", index)
        this.setItem(7, nbtItem.item)
    }

