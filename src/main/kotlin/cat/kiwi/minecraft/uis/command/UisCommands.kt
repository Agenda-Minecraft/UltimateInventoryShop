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

class UisCommands : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            UltimateInventoryShopPlugin.instance.logger.info("This command can only be used by players!")
            return true
        }
        val shopInventoryListener = InventoryBaseStructureController()
        if (args.isEmpty()) {
            shopInventoryListener.initGeneralStructure(sender)
            return true
        }
        when (args[0].lowercase()) {
            "open" -> {
                if (args.size == 1) {
                    shopInventoryListener.initGeneralStructure(sender)
                } else {
                    shopInventoryListener.initSpecifyStructure(sender, args[1])
                }

            }
            "sell" -> {
                SellController().sell(sender, args)
            }
            "help" -> {
                sender.sendMessage(Lang.helpMessage)
            }
            "reload" -> {
                // check permission
                if (!sender.hasPermission("uis.reload")) {
                    sender.sendMessage(Lang.noPermission)
                    return true
                }

                Config.readConfig(UltimateInventoryShopPlugin.instance)
                sender.sendMessage("${Lang.prefix}${Lang.reloadLang}")
            }
            "tax" -> {
                sender.sendMessage("${Lang.prefix}${Lang.taxRateInfo}${Config.taxRate}")
            }

            else -> {
                sender.sendMessage(Lang.helpMessage)
            }

        }
        return true
    }

}
