package main.me.sirlich.Prison.core;

import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugHandler implements CommandExecutor
{
    private static int debugLevel;

    public static int getDebugLevel(){
        return debugLevel;
    }

    public static void setDebugLevel(int debugLevel1){
        debugLevel = debugLevel1;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length < 1 || args[0].equalsIgnoreCase("help")){
                ChatUtils.chatCommand(player,"create, tag, list, delete, inside, view");
            }
            String type = args[0];

            //Set
            if (type.equalsIgnoreCase("set")) {
                setDebugLevel(Integer.parseInt(args[1]));
                ChatUtils.toolChat(player,"Debug level set to: " + getDebugLevel());
            } //Get
            if (type.equalsIgnoreCase("get")) {
                ChatUtils.toolChat(player,"Debug level: " + getDebugLevel());
            }
        }
        return true;
    }
}
