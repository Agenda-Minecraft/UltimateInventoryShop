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
            ShopStatus.ALLGOODS -> {
                goodsService.getPageNum()
            }

            ShopStatus.MYGOODS -> {
                goodsService.getPageNum(false, this.viewers[0] as Player)
            }

            ShopStatus.MYGOODSBEENSOLD -> {
                goodsService.getPageNum(true, this.viewers[0] as Player)
            }

            ShopStatus.SPECIFIEDPLAYER -> {
                goodsService.getPageNum(false, this.uisTargetPlayerUUID)
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

fun Inventory.resetStatus(status: ShopStatus) {
    UISLogger.debug("resetStatus: $status")
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
    UISLogger.debug("fillTable: ${this.uisStatus}")
    when (this.uisStatus) {
        ShopStatus.ALLGOODS -> {
            val goods = goodsService.getGoodsByIndex(this.uisIndex, false).list
            fillAndPadding(goods)
        }

        ShopStatus.MYGOODS -> {
            val goods = goodsService.getGoodsByPlayer(this.uisIndex, this.viewers[0] as Player).list
            fillAndPadding(goods)

        }

        ShopStatus.MYGOODSBEENSOLD -> {
            val goods = goodsService.getGoodsByPlayer(this.uisIndex, this.viewers[0] as Player, beenSold = true).list
            fillAndPadding(goods,beenSold = true)
        }

        ShopStatus.SPECIFIEDPLAYER -> {
            val goods = goodsService.getGoodsByPlayer(this.uisIndex, this.uisTargetPlayerUUID).list
            fillAndPadding(goods)
        }
    }
}

