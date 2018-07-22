package main.me.sirlich.Prison.zones;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ZoneCreator implements CommandExecutor, Listener
{
    private static Location zoneP1;
    private static Location zoneP2;
    private static String zoneName;
    private static ArrayList<String> zoneTags = new ArrayList<String>(20);
    private static boolean zoneCreation;
    private static Player zoneCreator;
    private static int zoneCreationStep;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            if(args.length < 1 || args[0].equalsIgnoreCase("help")){
                return false;
            }
            String type = args[0];
            if (type.equalsIgnoreCase("create")) {
                zoneCreation = true;
                zoneCreationStep = 1;
                zoneCreator = player;
                zoneName = args[1];
                ChatUtils.toolChat(player,"Started creation of new zone");
                ChatUtils.basicChat(player,"Click to set location 1");
            } else if(type.equalsIgnoreCase("tag")){
                if(zoneCreation){
                    zoneTags.add(args[1]);
                    ChatUtils.toolChat(player,"Tag added to zone.");
                } else {
                    ChatUtils.chatError(player,"That command can only be used during the creation of a zone!");
                }
            } else if(type.equalsIgnoreCase("list")){
                File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
                FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);
                Set<String> zones = zoneConfig.getKeys(false);
                ChatUtils.toolChat(player,"Zones:");
                for(String zone : zones)
                {
                    ChatUtils.basicChat(player,zone);
                }
            } else if(type.equalsIgnoreCase("delete")){
                File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
                FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);
                String delete = args[1];
                if(zoneConfig.isSet(delete)){
                    zoneConfig.set(delete,null);
                    ChatUtils.chatWarning(player,"Zone deleted!");
                    try {
                        zoneConfig.save(zoneYml);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    ChatUtils.chatError(player,"That zone does not exist.");
                }
            } else if(type.equalsIgnoreCase("inside")){
                ArrayList<String> zones = ZoneHandler.getZones(player);
                ChatUtils.toolChat(player,"You are inside the following zones:");
                for(String zone : zones)
                {
                    ChatUtils.basicChat(player,zone);
                }
            } else if(type.equalsIgnoreCase("view")){
                File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
                FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);
                String zone = args[1];
                if(zoneConfig.isSet(zone)){
                    ArrayList<String> tags = ZoneHandler.getZoneTags(zone);
                    ChatUtils.toolChat(player,"This zone has the following tags:");
                    for(String tag : tags)
                    {
                        ChatUtils.basicChat(player,tag);
                    }
                } else {
                    ChatUtils.chatError(player,"That zone does not exist.");
                }
            }
        }
        return true;
    }

    private void createZone(){
        File zoneYml = new File(Prison.getInstance().getDataFolder() + "/zones.yml");
        FileConfiguration zoneConfig = YamlConfiguration.loadConfiguration(zoneYml);

        if (zoneYml.exists()) {
            int p1x = (int) zoneP1.getX();
            int p1y = (int) zoneP1.getY();
            int p1z = (int) zoneP1.getZ();

            int p2x = (int) zoneP2.getX();
            int p2y = (int) zoneP2.getY();
            int p2z = (int) zoneP2.getZ();

            zoneConfig.set(zoneName + ".location.p1.x",Math.min(p1x,p2x));
            zoneConfig.set(zoneName + ".location.p1.y",Math.min(p1y,p2y));
            zoneConfig.set(zoneName + ".location.p1.z",Math.min(p1z,p2z));
            zoneConfig.set(zoneName + ".location.p2.x",Math.max(p1x,p2x));
            zoneConfig.set(zoneName + ".location.p2.y",Math.max(p1y,p2y));
            zoneConfig.set(zoneName + ".location.p2.z",Math.max(p1z,p2z));


            zoneConfig.set(zoneName + ".name",zoneName);
            zoneConfig.set(zoneName + ".tags",zoneTags);

            try {
                zoneConfig.save(zoneYml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void blockBreakEvent(BlockBreakEvent event){
        if(event.getPlayer() != null){
            Player player = (Player) event.getPlayer();
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            if(rpgPlayer.getPlayerState() == PlayerState.GOD && zoneCreation && event.getPlayer() == zoneCreator){
                event.setCancelled(true);
                Location location = event.getBlock().getLocation();
                if(zoneCreationStep == 1){
                    ChatUtils.toolChat(player,"Location 1 set");
                    ChatUtils.basicChat(player,"Click to set location 2");
                    zoneP1 = location;
                    zoneCreationStep = 2;
                } else if(zoneCreationStep == 2) {
                    ChatUtils.toolChat(player, "Location 2 set");
                    ChatUtils.basicChat(player,"Click to finish creation.");
                    zoneP2 = location;
                    zoneCreationStep = 3;
                } else if(zoneCreationStep == 3){
                    ChatUtils.toolChat(player,"Zone has been successfully created!");
                    createZone();
                    zoneCreation = false;
                    zoneTags.clear();
                }
            }
        }
    }
}
