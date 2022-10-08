package cat.kiwi.minecraft.uis.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


val ItemStack.serializedJson: String
    get() {
        val byteArrayInputStream = ByteArrayOutputStream()
        val bukkitObjectOutputStream = BukkitObjectOutputStream(byteArrayInputStream)
        bukkitObjectOutputStream.writeObject(this)
        bukkitObjectOutputStream.flush()

        val serializedObject: ByteArray = byteArrayInputStream.toByteArray()

        return String(Base64.getEncoder().encode(serializedObject))
    }

val String.b64Deserialized: ItemStack
    get() {
        val serializedObject = Base64.getDecoder().decode(this)
        val byteArrayInputStream = ByteArrayInputStream(serializedObject)

        return BukkitObjectInputStream(byteArrayInputStream).readObject() as ItemStack
    }
