package main.me.sirlich.Prison.zones;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ZoneHandler
{

    public static ArrayList<String> getZones(Player player){
        Location location = player.getLocation();
        ArrayList<String> zones = new ArrayList<String>(50);
        File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
        FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);
        for (String zone : zoneConfig.getKeys(false)) {
            if(locationInsideZone(location,zone)){
                zones.add(zoneConfig.getString(zone + ".name"));
            }
        }

        return zones;
    }

    public static ArrayList<String> getZoneTags(String zone){
        File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
        FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);

        List<String> tags = zoneConfig.getStringList(zone + ".tags");
        ArrayList<String> tagsArrayList = new ArrayList<String>(tags.size());
        tagsArrayList.addAll(tags);

        return tagsArrayList;
    }

    public static String getPvpZone(Player player){
        ArrayList<String> zones = getZones(player);
        File zoneYml = new File(Prison.getInstance().getDataFolder() + "/arenas.yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(zoneYml);
        for(String arena : arenaConfig.getKeys(false)){
            String zone = arenaConfig.getString(arena + ".zone");
            if(playerInsideZone(player,zone)){
                return arena;
            }
        }
        return null;
    }

    public static boolean playerInsideZone(Player player, String zone){
        Location location = player.getLocation();
        File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
        FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);
        return (zoneConfig.isSet(zone) && locationInsideZone(location,zone));
    }


    public static boolean playerInsideZoneTag(Player player, String zoneTag){
        ArrayList<String> zones = getZones(player);
        File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
        FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);
        for(String zone : zones){
            List<String> tags = zoneConfig.getStringList(zone + ".tags");
            for(String tag : tags){
                if(tag.equals(zoneTag)){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean locationInsideZone(Location location, String zone){
        File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
        FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);
        int p1x = zoneConfig.getInt(zone + ".location.p1.x");
        int p1y = zoneConfig.getInt(zone + ".location.p1.y");
        int p1z = zoneConfig.getInt(zone + ".location.p1.z");
        int p2x = zoneConfig.getInt(zone + ".location.p2.x");
        int p2y = zoneConfig.getInt(zone + ".location.p2.y");
        int p2z = zoneConfig.getInt(zone + ".location.p2.z");
        return(location.getX() >= p1x &&
                location.getX() <= p2x &&
                location.getY() >= p1y &&
                location.getY() <= p2y &&
                location.getZ() >= p1z &&
                location.getZ() <= p2z);
    }


}
