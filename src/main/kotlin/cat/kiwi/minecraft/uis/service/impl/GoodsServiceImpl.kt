package cat.kiwi.minecraft.uis.service.impl

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.mapper.GoodsMapper
import cat.kiwi.minecraft.uis.model.entity.GoodPojo
import cat.kiwi.minecraft.uis.service.GoodsService
import cat.kiwi.minecraft.uis.utils.serializedJson
import cat.kiwi.minecraft.uis.utils.tag
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.Date
import java.util.UUID


class GoodsServiceImpl : GoodsService {
    private val goodsMapper: GoodsMapper = UltimateInventoryShopPlugin.sqlSession.getMapper(GoodsMapper::class.java)
    override fun sellGoods(player: Player, goods: ItemStack, price: Double, description: String) {
        val currentDate = Date()
        val emptyDate = Date(0)

        val goodPojo = GoodPojo(
            UUID.randomUUID().toString(),
            player.name,
            player.uniqueId.toString(),
            "",
            "",
            price,
            goods.serializedJson,
            goods.tag,
            description,
            false,
            currentDate,
            emptyDate,
            ""
        )
        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            try {
                goodsMapper.sellGoods(goodPojo)
                UltimateInventoryShopPlugin.sqlSession.commit()
                player.inventory.removeItem(goods)
                player.sendMessage("${Lang.sellSuc}")

            } catch (e: Exception) {
                player.sendMessage("${Lang.sellFail}")
                UltimateInventoryShopPlugin.instance.logger.warning(e.message)
            }

        })


    }

    override fun getGoodsByIndex(index: Int, beenSold: Boolean): PageInfo<GoodPojo> {
        return PageHelper.startPage<GoodPojo>(index, 40).doSelectPageInfo { goodsMapper.getAllGoods(beenSold) }
    }

    override fun getAllGoods(index: Int, beenSold: Boolean): List<GoodPojo> {
        return goodsMapper.getAllGoods(beenSold)
    }

    override fun getGoodsByPlayer(index: Int, player: Player, beenSold: Boolean): PageInfo<GoodPojo> {
        return PageHelper.startPage<GoodPojo>(index, 40)
            .doSelectPageInfo { goodsMapper.getGoodsByPlayer(player.uniqueId.toString(), beenSold) }
    }

    override fun getGoodsByType(index: Int, type: String, beenSold: Boolean): PageInfo<GoodPojo> {
        return PageHelper.startPage<GoodPojo>(index, 40).doSelectPageInfo { goodsMapper.getGoodsByType(type, beenSold) }
    }

    override fun buyGoods(player: Player, goodUid: String) {
        goodsMapper.buyGoods(player.name, player.uniqueId.toString(), goodUid)
    }

    override fun deleteGoods(player: Player, goodUid: String) {
        goodsMapper.deleteGoods(goodUid)
    }

    override fun queryGoods(goodUid: String): GoodPojo {
        return goodsMapper.queryGoods(goodUid)
    }
}