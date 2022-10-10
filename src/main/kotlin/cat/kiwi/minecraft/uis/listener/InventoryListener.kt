package cat.kiwi.minecraft.uis.listener

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.model.enum.ShopStatus
import cat.kiwi.minecraft.uis.model.enum.UisButton
import cat.kiwi.minecraft.uis.utils.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        // basic check
        if (e.currentItem == null) return
        if (e.currentItem!!.type.isAir) return

        // prevent itemMovement
        if (!e.inventory.uisIdentity) return
        if(e.currentItem!!.getUisCondition() != null) {
            e.isCancelled = true
        }
        UisLogger.debug(e.currentItem, this::class.java)

        // meltdown for logic error
        if (!e.isCancelled) {
            UisLogger.panic("InventoryClickEvent is not cancelled", this::class.java)
            e.whoClicked.closeInventory()
            e.isCancelled = true
        }

        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            val condition = e.currentItem!!.getUisCondition()
            UisLogger.debug("condition: $condition")
            UisLogger.debug("status : ${e.inventory.uisStatus}")
            when (condition) {
                UisButton.PREVIOUS_PAGE -> {
                    var index = e.inventory.uisIndex
                    if (index <= 1) {
                        return@Runnable
                    }
                    index--
                    e.inventory.uisIndex = index
                    e.inventory.fillTable()
                }

                UisButton.NEXT_PAGE -> {
                    var index = e.inventory.uisIndex
                    if (index >= e.inventory.pageNum) {
                        return@Runnable
                    }
                    index++
                    e.inventory.uisIndex = index
                    e.inventory.fillTable()
                }

                UisButton.ALL_GOODS -> {
                    e.inventory.resetStatus(ShopStatus.ALL_GOODS)
                }

                UisButton.MY_GOODS -> {
                    e.inventory.resetStatus(ShopStatus.MY_GOODS)
                }

                UisButton.MY_GOODS_BEEN_SOLD-> {
                    e.inventory.resetStatus(ShopStatus.MY_GOODS_BEEN_SOLD)
                }

                UisButton.GOODS_ITEM -> {
                    when (e.inventory.uisStatus) {
                        ShopStatus.ALL_GOODS -> {
                            Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
                                UltimateInventoryShopPlugin.goodsService.buyGoods(
                                    e.whoClicked as Player, e.currentItem!!.getUisItemMeta("id"), e.inventory
                                )
                            })
                        }

                        ShopStatus.MY_GOODS -> {
                            Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
                                UltimateInventoryShopPlugin.goodsService.deleteGoods(
                                    e.whoClicked as Player, e.currentItem!!.getUisItemMeta("id"), e.inventory
                                )
                            })
                        }

                        ShopStatus.SPECIFIED_PLAYER -> {
                            Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
                                UltimateInventoryShopPlugin.goodsService.buyGoods(
                                    e.whoClicked as Player, e.currentItem!!.getUisItemMeta("id"), e.inventory
                                )
                            })

                        }
                        else -> {
                        }
                    }
                }
                else -> {
                }
            }
        })
    }
}