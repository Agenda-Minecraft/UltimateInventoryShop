package cat.kiwi.minecraft.uis.utils

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.consts.indexDescriptionTable
import cat.kiwi.minecraft.uis.model.enum.ShopStatus
import cat.kiwi.minecraft.uis.model.pojo.GoodPojo
import cat.kiwi.minecraft.uis.model.pojo.renderedGoods
import cat.kiwi.minecraft.uis.model.pojo.renderedGoodsBeenSold
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
val Inventory.pageNum: Int
    get() {
        val goodsService = UltimateInventoryShopPlugin.goodsService
        return when (this.uisStatus) {
            ShopStatus.ALL_GOODS -> {
                goodsService.getPageNum()
            }

            ShopStatus.MY_GOODS -> {
                goodsService.getPageNum(false, this.viewers[0] as Player)
            }

            ShopStatus.MY_GOODS_BEEN_SOLD -> {
                goodsService.getPageNum(true, this.viewers[0] as Player)
            }

            ShopStatus.SPECIFIED_PLAYER -> {
                goodsService.getPageNum(false, this.uisTargetPlayerUUID)
            }

            else -> 1
        }
    }

fun Inventory.setShopItem(index: Int, itemStack: ItemStack?) {
    if (index < 0 || index > 39) {
        return
    }
    this.setItem(indexDescriptionTable[index], itemStack)
}

fun Inventory.resetStatus(status: ShopStatus) {
    UisLogger.debug("resetStatus: $status")
    this.uisStatus = status
    this.uisIndex = 1
    this.fillTable()
}

fun Inventory.fillAndPadding(goodPojoList: List<GoodPojo>, beenSold: Boolean = false) {
    // fill
    goodPojoList.forEachIndexed { i, good ->
        if (beenSold) {
            this.setShopItem(i, good.renderedGoodsBeenSold)
        } else {
            this.setShopItem(i, good.renderedGoods)
        }
    }
    // padding
    (goodPojoList.size..40).forEach {
        this.setShopItem(it, null)
    }
}

fun Inventory.fillTable() {
    val goodsService = UltimateInventoryShopPlugin.goodsService
    UisLogger.debug("fillTable: ${this.uisStatus}", this::class.java)
    when (this.uisStatus) {
        ShopStatus.ALL_GOODS -> {
            val goods = goodsService.getGoodsByIndex(this.uisIndex, false).list
            fillAndPadding(goods)
        }

        ShopStatus.MY_GOODS -> {
            val goods = goodsService.getGoodsByPlayer(this.uisIndex, this.viewers[0] as Player).list
            fillAndPadding(goods)

        }

        ShopStatus.MY_GOODS_BEEN_SOLD -> {
            val goods = goodsService.getGoodsByPlayer(this.uisIndex, this.viewers[0] as Player, beenSold = true).list
            fillAndPadding(goods,beenSold = true)
        }

        ShopStatus.SPECIFIED_PLAYER -> {
            val goods = goodsService.getGoodsByPlayer(this.uisIndex, this.uisTargetPlayerUUID).list
            fillAndPadding(goods)
        }

        else -> {
            UisLogger.panic("fillTable: else", this::class.java)
        }
    }
}

