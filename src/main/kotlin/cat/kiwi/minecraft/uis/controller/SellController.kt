package cat.kiwi.minecraft.uis.controller

import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.service.GoodsService
import cat.kiwi.minecraft.uis.service.impl.GoodsServiceImpl
import org.bukkit.Material
import org.bukkit.entity.Player

class SellController {
    private val goodsService: GoodsService = GoodsServiceImpl()
    fun sell(sender: Player, args: Array<out String>): Boolean {
        // uis sell <price> <description>
        if (args.size < 2) {
            sender.sendMessage("${Lang.sellUsage}")
            return false
        }

        val price = args[1].toDoubleOrNull()
        if (price == null) {
            sender.sendMessage("${Lang.sellUsage}")
            return false
        }
        val description = if (args.size > 2) {
            args[2]
        } else {
            ""
        }

        val itemStack = sender.inventory.itemInMainHand
        if (itemStack.type == Material.AIR) {
            sender.sendMessage("${Lang.emptyInMainHand}")
            return false
        }
        goodsService.sellGoods(sender, itemStack, price, description)
        return true
    }

}