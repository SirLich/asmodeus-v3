package main.me.sirlich.Prison.handlers;

import main.me.sirlich.Prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class GateHandler
{

    public static void giveGatePermision(Player player, int gateNumber){
        String playerUUID = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUUID + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
            playerConfig.set("gates." + gateNumber,true);
            try {
                playerConfig.save(playerYml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static boolean hasGatePermision(Player player, Block block){
        System.out.println(block.getData());
        String playerUUID = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUUID + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
            return playerConfig.getBoolean("gates." + block.getData());
        }
        return false;
    }
    public static void runGateTick(){
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            Location east = new Location(player.getWorld(), 1,0,0);
            Block east_block = player.getEyeLocation().add(east).getBlock();

            Location west = new Location(player.getWorld(), -1,0,0);
            Block west_block = player.getEyeLocation().add(west).getBlock();

            Location north = new Location(player.getWorld(), 0,0,1);
            Block north_block = player.getEyeLocation().add(north).getBlock();

            Location south = new Location(player.getWorld(), 0,0,-1);
            Block south_block = player.getEyeLocation().add(south).getBlock();

            if(east_block.getType() == Material.STAINED_GLASS){
                east = east.multiply(3);
                if(hasGatePermision(player, east_block)){
                    player.teleport(player.getLocation().add(east));
                }
            }
            if(west_block.getType() == Material.STAINED_GLASS){
                west = west.multiply(3);
                if(hasGatePermision(player, west_block)){
                    player.teleport(player.getLocation().add(west));
                }
            }
            if(north_block.getType() == Material.STAINED_GLASS){
                north = north.multiply(3);
                if(hasGatePermision(player, north_block)){
                    player.teleport(player.getLocation().add(north));
                }
            }
            if(south_block.getType() == Material.STAINED_GLASS){
                south = south.multiply(3);
                if(hasGatePermision(player, south_block)){
                    player.teleport(player.getLocation().add(south));
                }
            }
        }
    }
}
