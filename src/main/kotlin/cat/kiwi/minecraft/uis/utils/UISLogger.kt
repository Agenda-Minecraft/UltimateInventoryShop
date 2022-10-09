package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin

object UISLogger {
    fun debug(any: Any?) {
        if (UltimateInventoryShopPlugin.instance.config.getBoolean("debug")) {
            UltimateInventoryShopPlugin.instance.logger.info("[DEBUG] $any")
        }
    }
}