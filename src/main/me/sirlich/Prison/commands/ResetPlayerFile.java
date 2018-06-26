package main.me.sirlich.Prison.commands;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.handlers.PlayerJoinHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;


public class ResetPlayerFile implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            File file = new File(Prison.getInstance().getDataFolder() + "/players/" + player.getUniqueId() + ".yml");
            file.delete();
            player.getInventory().clear();
            player.kickPlayer("Login again for a fresh start!");
        }
        return true;
    }
}
