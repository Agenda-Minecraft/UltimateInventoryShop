package cat.kiwi.minecraft.uis.command

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Config
import cat.kiwi.minecraft.uis.config.Lang
import cat.kiwi.minecraft.uis.controller.InventoryBaseStructureController
import cat.kiwi.minecraft.uis.controller.SellController
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UisErrorCommand: CommandExecutor {
        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
            sender.sendMessage("Plugin is not loaded")
            return true
        }
}