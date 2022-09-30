package cat.kiwi.minecraft.uis.service

import cat.kiwi.minecraft.uis.model.entity.Goods
import cat.kiwi.minecraft.uis.model.page.GoodsPage
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface GoodsService {
    fun getAllGoods(index: Int): GoodsPage

    fun getGoodsByPlayer(index: Int, player: Player): GoodsPage

    fun getGoodsByType(index: Int, type: String): GoodsPage

    fun sellGoods(player: Player, goods: ItemStack, price: Int)

    fun buyGoods(player: Player, goodUid: String)

    fun deleteGoods(player: Player, goodUid: String)

}