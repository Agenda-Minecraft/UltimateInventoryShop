package cat.kiwi.minecraft.uis.service

import cat.kiwi.minecraft.uis.model.pojo.GoodPojo
import com.github.pagehelper.PageInfo
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

interface GoodsService {
    fun sellGoods(player: Player, goods: ItemStack, price: Double, description: String)

    fun getAllGoods(index: Int, beenSold: Boolean = false): List<GoodPojo>

    fun getGoodsByIndex(index: Int, beenSold: Boolean = false): PageInfo<GoodPojo>

    fun getGoodsByPlayer(index: Int, player: Player, beenSold: Boolean = false): PageInfo<GoodPojo>
    fun getGoodsByPlayer(index: Int, uuid: UUID, beenSold: Boolean = false): PageInfo<GoodPojo>

    fun getGoodsByType(index: Int, type: String, beenSold: Boolean = false): PageInfo<GoodPojo>

    fun buyGoods(player: Player, goodUid: String, inventory: Inventory)

    fun deleteGoods(player: Player, goodUid: String, inventory: Inventory)

    fun queryGoods(goodUid: String): GoodPojo?

    fun getPageNum(beenSold: Boolean=false): Int
    fun getPageNum(beenSold: Boolean=false, player: Player): Int
    fun getPageNum(beenSold: Boolean=false, uuid: UUID): Int

}