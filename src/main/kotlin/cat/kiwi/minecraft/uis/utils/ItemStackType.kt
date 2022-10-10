package cat.kiwi.minecraft.uis.utils

import org.bukkit.inventory.ItemStack

val ItemStack.tag: String
    get() {
        return this.type.let {
            if (it.isEdible) {
                "food"
            } else if (it.isBlock) {
                "block"
            } else if (it.isFuel) {
                "fuel"
            } else {
                "other"
            }
        }
    }