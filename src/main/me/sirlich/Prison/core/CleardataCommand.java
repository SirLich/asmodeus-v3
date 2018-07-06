package main.me.sirlich.Prison.core;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.handlers.PlayerJoinHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;


public class CleardataCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            rpgPlayer.clearPlayerFile();
        }
        return true;
    }
}
