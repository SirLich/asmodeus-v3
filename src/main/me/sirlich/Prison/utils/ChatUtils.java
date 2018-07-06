package main.me.sirlich.Prison.utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPickupArrowEvent;

import java.util.List;
import java.util.Random;

public class ChatUtils
{

    public static void shopChat(Player p, String m){
        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "!" + ChatColor.DARK_GREEN + "] " + ChatColor.WHITE + m);
    }
    public static void tutorialChat(Player p, String m){
        p.sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + "!" + ChatColor.DARK_PURPLE + "] " + ChatColor.WHITE + m);
        Sound sound = Sound.ENTITY_ARROW_HIT_PLAYER;
        p.playSound(p.getLocation(),sound,1,1);
    }
    public static void toolChat(Player p, String m){
        p.sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + "!" + ChatColor.DARK_PURPLE + "] " + ChatColor.WHITE + m);
        Sound sound = Sound.ENTITY_ARROW_HIT_PLAYER;
        p.playSound(p.getLocation(),sound,1,1);
    }
    public static void chatInfo(Player p, String m)
    {
        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "!" + ChatColor.DARK_GREEN + "] " + ChatColor.WHITE + m);
    }

    public static void chatWarning(Player p, String m)
    {
        p.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "!" + ChatColor.GOLD + "] " + ChatColor.WHITE + m);
    }

    public static void chatCommand(Player p, String m){
        p.sendMessage(ChatColor.RED + "Missing or incorrect argument. Try: " + ChatColor.LIGHT_PURPLE + m);
    }
    public static void chatError(Player p, String m)
    {
        p.sendMessage(ChatColor.RED + m);
    }

    public static void civilianChat(Player p, String nickname, List<String> list){
        Random r = new Random();
        String m = list.get(r.nextInt(list.size()));
        civilianChat(p,nickname,m);
    }

    public static void basicChat(Player p, String m){
        p.sendMessage(ChatColor.GRAY + "- " + m);
    }
    public static void civilianChat(Player p, String nickname, String m)
    {
        p.sendMessage(ChatColor.GRAY + nickname + " " + ChatColor.WHITE + m);
    }

    public static void abilitiesChat(Player p, String m)
    {
        p.sendMessage(ChatColor.AQUA + m);
    }
}
