package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShop
import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Config

object UisLogger {
    fun debug(any: Any?) {
        if (Config.debug) {
            UltimateInventoryShopPlugin.instance.logger.info("[DEBUG] $any")
        }
    }
    fun debug(any: Any?, javaClass: Class<*>) {
        if (Config.debug) {
            UltimateInventoryShopPlugin.instance.logger.info("[DEBUG] ${javaClass.simpleName}: $any")
        }
    }

    fun panic(any: Any?, javaClass: Class<*>) {
        UltimateInventoryShopPlugin.instance.logger.warning("[PANIC] ==============================")
        UltimateInventoryShopPlugin.instance.logger.warning("[PANIC] ")
        UltimateInventoryShopPlugin.instance.logger.warning("[PANIC] LOGIC ERROR, PLEASE REPORT THIS TO DEVELOPER!")
        UltimateInventoryShopPlugin.instance.logger.warning("[PANIC] ")
        UltimateInventoryShopPlugin.instance.logger.warning("[PANIC] ==============================")
        UltimateInventoryShopPlugin.instance.logger.warning("[PANIC] ${javaClass.simpleName}: $any")
    }
}