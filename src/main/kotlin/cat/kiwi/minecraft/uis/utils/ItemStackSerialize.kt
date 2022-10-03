package cat.kiwi.minecraft.uis.utils

import com.google.gson.Gson
import org.bukkit.inventory.ItemStack
import java.io.ByteArrayInputStream
import java.io.IOException


val ItemStack.serializedJson: String
    get() {
        val gson = Gson()
        return gson.toJson(this.serialize(), Map::class.java)
    }

val String.deserializedJson: ItemStack
    get() {
        val gson = Gson()
        val resultMap = gson.fromJson<Map<String, Any>>(this, Map::class.java)
        return ItemStack.deserialize(resultMap)
    }