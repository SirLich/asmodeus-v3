package main.me.sirlich.Prison.commands;

import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPlayerState implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            String state = args[0];
            if(state.equalsIgnoreCase("god")){
                rpgPlayer.setPlayerState(PlayerState.GOD);
                ChatUtils.chatInfo(player,"Player state set to: god");
            } else if(state.equalsIgnoreCase("basic")){
                rpgPlayer.setPlayerState(PlayerState.BASIC);
                ChatUtils.chatInfo(player,"Player state set to: basic");
            }
        }
        return true;
    }
}
