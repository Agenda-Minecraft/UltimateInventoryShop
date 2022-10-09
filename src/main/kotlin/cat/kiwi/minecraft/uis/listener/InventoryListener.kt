package cat.kiwi.minecraft.uis.listener

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.model.enum.ShopStatus
import cat.kiwi.minecraft.uis.utils.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (e.currentItem == null) return
        if (e.currentItem!!.type.isAir) return
        UISLogger.debug(e.currentItem)

        if (!e.inventory.uisIdentity) return

        e.isCancelled = true

        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            val condition = e.currentItem!!.getUisCondition()
            UISLogger.debug("condition: $condition")
            UISLogger.debug("status : ${e.inventory.uisStatus}")
            when (condition) {
                "previousPage" -> {
                    var index = e.inventory.uisIndex
                    if (index <= 1) {
                        return@Runnable
                    }
                    index--
                    e.inventory.uisIndex = index
                    e.inventory.fillTable()
                }

                "nextPage" -> {
                    var index = e.inventory.uisIndex
                    if (index >= UltimateInventoryShopPlugin.goodsService.getPageNum(false)) {
                        return@Runnable
                    }
                    index++
                    e.inventory.uisIndex = index
                    e.inventory.fillTable()
                }

                "allGoods" -> {
                    e.inventory.resetStatus(ShopStatus.ALLGOODS)
                }

                "myGoods" -> {
                    e.inventory.resetStatus(ShopStatus.MYGOODS)
                }

                "myGoodsBeenSold" -> {
                    e.inventory.resetStatus(ShopStatus.MYGOODSBEENSOLD)
                }

                "goodsItem" -> {
                    when (e.inventory.uisStatus) {
                        ShopStatus.ALLGOODS -> {
                            Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
                                UltimateInventoryShopPlugin.goodsService.buyGoods(
                                    e.whoClicked as Player, e.currentItem!!.getUisItemMeta("id"), e.inventory
                                )
                            })
                        }
                        ShopStatus.MYGOODS -> {}
                        ShopStatus.MYGOODSBEENSOLD -> {}
                        ShopStatus.SPECIFIEDPLAYER -> {
                            Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
                                UltimateInventoryShopPlugin.goodsService.buyGoods(
                                    e.whoClicked as Player, e.currentItem!!.getUisItemMeta("id"), e.inventory
                                )
                            })

                        }

                    }
                }
            }
        })
    }
}