package cat.kiwi.minecraft.uis.listener

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.utils.UISLogger
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class RecordListener : Listener {
    private val playerService = UltimateInventoryShopPlugin.playerService

    @EventHandler
    fun onPlayerJoinEvent(e: PlayerJoinEvent) {
        Bukkit.getScheduler().runTaskAsynchronously(UltimateInventoryShopPlugin.instance, Runnable {
            // optimize for general sql
            val sqlPlayerName = playerService.getPlayerNameByUUID(e.player.uniqueId)
            if (sqlPlayerName == null) {
                UISLogger.debug("Player ${e.player.name} is not in the database, adding...")
                playerService.insertPlayerName(e.player.uniqueId, e.player.name)
                return@Runnable
            } else if (sqlPlayerName == "") {
                UISLogger.debug("Player ${e.player.name} is not in the database, adding...")
                playerService.insertPlayerName(e.player.uniqueId, e.player.name)
                return@Runnable
            }
            if (sqlPlayerName != e.player.name) {
                UISLogger.debug("Player ${e.player.name} is in the database, but name is not the same, updating...")
                playerService.updatePlayerName(e.player.uniqueId, e.player.name)
            }
        })
    }
}