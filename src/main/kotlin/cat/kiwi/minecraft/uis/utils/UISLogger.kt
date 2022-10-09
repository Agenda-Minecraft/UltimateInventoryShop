package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Config

object UISLogger {
    fun debug(any: Any?) {
        if (Config.debug) {
            UltimateInventoryShopPlugin.instance.logger.info("[DEBUG] $any")
        }
    }
}