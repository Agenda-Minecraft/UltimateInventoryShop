package cat.kiwi.minecraft.uis.listener

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.utils.UisLogger
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
                UisLogger.debug("Player ${e.player.name} is not in the database, adding...", this::class.java)
                playerService.insertPlayerName(e.player.uniqueId, e.player.name)
                return@Runnable
            } else if (sqlPlayerName == "") {
                UisLogger.debug("Player ${e.player.name} is not in the database, adding...", this::class.java)
                playerService.insertPlayerName(e.player.uniqueId, e.player.name)
                return@Runnable
            }
            if (sqlPlayerName != e.player.name) {
                UisLogger.debug("Player ${e.player.name} is in the database, but name is not the same, updating...", this::class.java)
                playerService.updatePlayerName(e.player.uniqueId, e.player.name)
            }
        })
    }
}