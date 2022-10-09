package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.consts.indexDescriptionTable
import cat.kiwi.minecraft.uis.model.entity.GoodPojo
import cat.kiwi.minecraft.uis.model.entity.renderedGoods
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

var Inventory.specifiedPlayer: String
    set(value) {
        ""
    }
    get() {
        return ""
    }

val Inventory.pageNum: Int
    get() {
        val goodsService = UltimateInventoryShopPlugin.goodsService
        return when (this.uisStatus) {
            "allGoods" -> {
                goodsService.getPageNum()
            }

            "myGoods" -> {
                goodsService.getPageNum(false, this.viewers[0] as Player)
            }

            "myGoodsBeenSold" -> {
                goodsService.getPageNum(true, this.viewers[0] as Player)
            }
            "specifyPlayer" -> {
                goodsService.getPageNum(false,this.specifiedPlayer )
            }
            else -> {
                1
            }
        }
    }

fun Inventory.setShopItem(index: Int, itemStack: ItemStack?) {
    if (index < 0 || index > 39) {
        return
    }
    this.setItem(indexDescriptionTable[index], itemStack)
}

fun Inventory.resetStatus(status: String) {
    UltimateInventoryShopPlugin.instance.logger.info("resetStatus: $status")
    this.uisStatus = status
    this.uisIndex = 1
    this.fillTable()
}

fun Inventory.fillAndPadding(goodPojoList: List<GoodPojo>) {
    // fill
    goodPojoList.forEachIndexed { i, good ->
        this.setShopItem(i, good.renderedGoods)
    }
    // padding
    (goodPojoList.size..40).forEach {
        this.setShopItem(it, null)
    }
}

fun Inventory.fillTable() {
    val goodsService = UltimateInventoryShopPlugin.goodsService
    UISLogger.debug("fillTable: ${this.uisStatus}")
    when (this.uisStatus) {
        "allGoods" -> {
            val goods = goodsService.getGoodsByIndex(this.uisIndex, false).list
            fillAndPadding(goods)
        }

        "myGoods" -> {
            val goods = goodsService.getGoodsByPlayer(this.uisIndex, this.viewers[0] as Player).list
            fillAndPadding(goods)

        }

        "myGoodsBeenSold" -> {
            val goods = goodsService.getGoodsByPlayer(this.uisIndex, this.viewers[0] as Player, beenSold = true).list
            fillAndPadding(goods)
        }
    }
}

