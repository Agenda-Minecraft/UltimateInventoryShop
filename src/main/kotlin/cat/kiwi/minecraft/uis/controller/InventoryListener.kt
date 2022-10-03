package cat.kiwi.minecraft.uis.controller

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.Inventory

class InventoryListener(player: Player) : Listener {
    private var inventory: Inventory = Bukkit.createInventory(player, 54)

    private lateinit var mode: String

    private var beenSold: Boolean = false
    private var index: Int = 0







}