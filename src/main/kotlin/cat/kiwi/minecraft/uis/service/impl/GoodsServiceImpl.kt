package cat.kiwi.minecraft.uis.service.impl

import cat.kiwi.minecraft.uis.model.page.GoodsPage
import cat.kiwi.minecraft.uis.service.GoodsService
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack


class GoodsServiceImpl: GoodsService {
    override fun sellGoods(player: Player, goods: ItemStack, price: Int) {
    }

    override fun getAllGoods(index: Int): GoodsPage {
        TODO("Not yet implemented")
    }

    override fun getGoodsByPlayer(index: Int, player: Player): GoodsPage {
        TODO("Not yet implemented")
    }

    override fun getGoodsByType(index: Int, type: String): GoodsPage {
        TODO("Not yet implemented")
    }

    override fun buyGoods(player: Player, goodUid: String) {
        TODO("Not yet implemented")
    }

    override fun deleteGoods(player: Player, goodUid: String) {
        TODO("Not yet implemented")
    }
}