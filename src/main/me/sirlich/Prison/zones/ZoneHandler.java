package main.me.sirlich.Prison.zones;

import main.me.sirlich.Prison.Prison;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class ZoneHandler
{
    public static ArrayList<String> getZones(Player player){
        Location location = player.getLocation();
        ArrayList<String> zones = new ArrayList<String>(50);
        File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
        FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);
        for (String zone : zoneConfig.getKeys(false)) {
            int p1x = zoneConfig.getInt(zone + ".location.p1.x");
            int p1z = zoneConfig.getInt(zone + ".location.p1.z");
            int p2x = zoneConfig.getInt(zone + ".location.p2.x");
            int p2z = zoneConfig.getInt(zone + ".location.p2.z");
            if(playerInside(location,p1x,p1z,p2x,p2z)){
                zones.add(zoneConfig.getString(zone + ".name"));
            }
        }

        return zones;
    }

    private static boolean playerInside(Location location, int p1x, int p1z, int p2x, int p2z){
        System.out.println("Is the person inside???");
        return(location.getX() >= p1x &&
                location.getX() <= p2x &&
                location.getZ() >= p1z &&
                location.getZ() <= p2z);
    }
}
