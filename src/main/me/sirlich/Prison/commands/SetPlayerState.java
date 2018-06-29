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
            if(args.length < 1){
                ChatUtils.chatWarning(player,"Please include an argument: state");
                return true;
            }
            if(args[0].equalsIgnoreCase("state")){
                if(args.length < 2){
                    ChatUtils.chatWarning(player,"Please include a state: basic, god, pvp");
                    return true;
                }
                String state = args[1];
                if(state.equalsIgnoreCase("god")){
                    rpgPlayer.setPlayerState(PlayerState.GOD);
                    ChatUtils.toolChat(player, "Player state set.");
                } else if(state.equalsIgnoreCase("basic")){
                    rpgPlayer.setPlayerState(PlayerState.BASIC);
                    ChatUtils.toolChat(player, "Player state set.");
                }
                else if(state.equalsIgnoreCase("pvp")){
                    rpgPlayer.setPlayerState(PlayerState.PVP);
                    ChatUtils.toolChat(player, "Player state set.");
                } else {
                    ChatUtils.chatWarning(player,"Please include a state: basic, god, pvp");
                }
            } else {
                ChatUtils.chatWarning(player,"Please include an argument: state");
            }
        }
        return true;
    }
}
