package main.me.sirlich.Prison.core;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StateCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            if(args.length < 1){
                ChatUtils.chatCommand(player,"set, get");
                return true;
            }
            if(args[0].equalsIgnoreCase("set")){
                if(args.length < 2){
                    ChatUtils.chatCommand(player,"basic, god, pvp, tutorial, plots");
                    return true;
                }
                String state = args[1];
                if(args.length >= 3){
                    String playerName = args[2];
                    Player setPlayer = Prison.getInstance().getServer().getPlayer(playerName);
                    rpgPlayer = RpgPlayerList.getRpgPlayer(setPlayer);
                }
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
                } else if(state.equalsIgnoreCase("tutorial")){
                    rpgPlayer.setPlayerState(PlayerState.TUTORIAL);
                    ChatUtils.toolChat(player, "Player state set.");
                } else if(state.equalsIgnoreCase("plots")){
                    rpgPlayer.setPlayerState(PlayerState.PLOTS);
                    ChatUtils.toolChat(player, "Player state set.");
                }else {
                    ChatUtils.chatCommand(player,"basic, god, pvp, tutorial, plots");
                }
            } else if(args[0].equalsIgnoreCase("get")){
                if(args.length >= 2){
                    String playerName = args[1];
                    Player setPlayer = Prison.getInstance().getServer().getPlayer(playerName);
                    rpgPlayer = RpgPlayerList.getRpgPlayer(setPlayer);
                }
                ChatUtils.toolChat(player,"Player state: " + rpgPlayer.getPlayerState().toString());
            }else {
                ChatUtils.chatCommand(player,"set, get");
            }
        }
        return true;
    }
}
