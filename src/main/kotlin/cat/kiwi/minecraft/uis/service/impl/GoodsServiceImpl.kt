package cat.kiwi.minecraft.uis.service.impl

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Config
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.mapper.GoodsMapper
import cat.kiwi.minecraft.uis.model.entity.GoodPojo
import cat.kiwi.minecraft.uis.service.GoodsService
import cat.kiwi.minecraft.uis.utils.b64Deserialized
import cat.kiwi.minecraft.uis.utils.fillTable
import cat.kiwi.minecraft.uis.utils.serializedJson
import cat.kiwi.minecraft.uis.utils.tag
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*


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
        try {
            goodsMapper.sellGoods(goodPojo)
            player.inventory.removeItem(goods)
            player.sendMessage("${Lang.prefix}${Lang.sellSuc}")
            UltimateInventoryShopPlugin.sqlSession.commit()
        } catch (e: Exception) {
            player.sendMessage("${Lang.prefix}${Lang.sellFail}")
            UltimateInventoryShopPlugin.instance.logger.warning(e.message)
        }


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

    override fun buyGoods(player: Player, goodUid: String, inventory: Inventory) {
        try {
            val goodPojo = goodsMapper.queryGoods(goodUid)

            if (goodPojo == null) {
                player.sendMessage("${Lang.prefix}${Lang.invalidItem}")
                return
            }
            if (goodPojo.beenSold) {
                player.sendMessage("${Lang.prefix}${Lang.buyFail}")
                return
            }

            val price: Double = if (Config.enableTax) {
                goodPojo.price + Config.taxRate * goodPojo.price
            } else {
                goodPojo.price
            }
            if (price > UltimateInventoryShopPlugin.economy.getBalance(player)) {
                player.sendMessage("${Lang.prefix}${Lang.noEnoughMoney}")
                return
            }

            // check player inventory is full
            if (player.inventory.firstEmpty() == -1) {
                player.sendMessage("${Lang.prefix}${Lang.invFull}")
                return
            }

            goodsMapper.buyGoods(player.name, player.uniqueId.toString(), goodUid)

            UltimateInventoryShopPlugin.economy.withdrawPlayer(player, price)
            UltimateInventoryShopPlugin.economy.depositPlayer(
                Bukkit.getOfflinePlayer(UUID.fromString(goodPojo.putterUid)),
                goodPojo.price
            )
            player.inventory.addItem(goodPojo.itemInfo.b64Deserialized)
            player.sendMessage("${Lang.prefix}${Lang.buySuc}")
            UltimateInventoryShopPlugin.sqlSession.commit()
        } catch (e: Exception) {
            player.sendMessage("${Lang.prefix}${Lang.buyFail}")
            UltimateInventoryShopPlugin.instance.logger.warning(e.message)
        }
        inventory.fillTable()
    }

    override fun deleteGoods(player: Player, goodUid: String) {
        goodsMapper.deleteGoods(goodUid)

        UltimateInventoryShopPlugin.sqlSession.commit()
    }

    override fun queryGoods(goodUid: String): GoodPojo? {
        return goodsMapper.queryGoods(goodUid)
    }

    override fun getPageNum(beenSold: Boolean): Int {
        return goodsMapper.getCount(beenSold = beenSold) / 40 + 1
    }

    override fun getPageNum(beenSold: Boolean, player: Player): Int {
        return goodsMapper.getCountPlayer(beenSold = beenSold, putterName = player.uniqueId.toString()) / 40 + 1
    }

    override fun getPageNum(beenSold: Boolean, playerName: String): Int {
        return goodsMapper.getCountPlayer(beenSold = beenSold, putterName = playerName) / 40 + 1
    }

    override fun getGoodsByPlayer(index: Int, uuid: UUID, beenSold: Boolean): PageInfo<GoodPojo> {
        return PageHelper.startPage<GoodPojo>(index, 40)
            .doSelectPageInfo { goodsMapper.getGoodsByPlayer(uuid.toString(), beenSold) }
    }
}