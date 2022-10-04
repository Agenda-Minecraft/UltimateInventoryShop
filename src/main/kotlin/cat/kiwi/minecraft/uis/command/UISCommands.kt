package cat.kiwi.minecraft.uis.command

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.controller.InventoryListener
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UISCommands: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            UltimateInventoryShopPlugin.instance.logger.info("This command can only be used by players!")
            return true
        }

        if (args.isEmpty()) return true
        when (args[0]) {
            "open" -> {
                val inventoryListener = InventoryListener(sender)
                sender.openInventory(inventoryListener.inventory)
            }
            else -> {

            }

        }


        return true
    }

}

// /uis help
// /uis open
// /uis sell <price> <description>
