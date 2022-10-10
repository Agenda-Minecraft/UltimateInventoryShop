package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.service.GoodsService
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SellController {
    private val goodsService: GoodsService = UltimateInventoryShopPlugin.goodsService
    fun sell(sender: Player, args: Array<out String>): Boolean {
        // uis sell <price> <description>
        // uis sell <price>
        if (args.size < 2) {
            sender.sendMessage("${Lang.prefix}${Lang.sellUsage}")
            return false
        }

        val price = args[1].toDoubleOrNull()

        if (price == null) {
            sender.sendMessage("${Lang.prefix}${Lang.sellUsage}")
            return false
        }
        if (price < 0.0) {
            sender.sendMessage("${Lang.prefix}${Lang.sellPriceError}")
            return false
        }
        val description = if (args.size > 2) {
            args[2]
        } else {
            ""
        }

        val itemStack = sender.inventory.itemInMainHand
        if (itemStack.type.isAir) {
            sender.sendMessage("${Lang.prefix}${Lang.emptyInMainHand}")
            return false
        }
        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            goodsService.sellGoods(sender, itemStack, price, description)
        })
        return true
    }
}