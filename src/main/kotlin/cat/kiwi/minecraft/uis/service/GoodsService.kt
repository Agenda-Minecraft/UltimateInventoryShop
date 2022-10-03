package cat.kiwi.minecraft.uis.service

import cat.kiwi.minecraft.uis.model.entity.Good
import com.github.pagehelper.Page
import com.github.pagehelper.PageInfo
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface GoodsService {
    fun sellGoods(player: Player, goods: ItemStack, price: Double, description: String)

    fun getAllGoods(index: Int, beenSold: Boolean = false): List<Good>

    fun getGoodsByIndex(index: Int, beenSold: Boolean = false): PageInfo<Good>

    fun getGoodsByPlayer(index: Int, player: Player, beenSold: Boolean = false): PageInfo<Good>

    fun getGoodsByType(index: Int, type: String, beenSold: Boolean = false): PageInfo<Good>

    fun buyGoods(player: Player, goodUid: String)

    fun deleteGoods(player: Player, goodUid: String)

    fun queryGoods(goodUid: String): Good

}