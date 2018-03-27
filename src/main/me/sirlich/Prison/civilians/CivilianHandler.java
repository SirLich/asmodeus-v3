package main.me.sirlich.Prison.civilians;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.io.*;
import java.util.*;

public class CivilianHandler implements Listener
{

    public static HashMap<UUID, List<String>> messageMap = new HashMap<UUID,List<String>>();
    public static HashMap<UUID, String> mobIDMap = new HashMap<UUID, String>();

    private static void addCivilianToList(UUID uuid, List<String> messages, String mobID){
        messageMap.put(uuid,messages);
        mobIDMap.put(uuid,mobID);
    }

    public static List<String> getMessages(UUID uuid){
        return messageMap.get(uuid);
    }

    public static String getMobID(UUID uuid){
        return mobIDMap.get(uuid);
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event)
    {
        if(event.getRightClicked() instanceof Villager){
            event.setCancelled(true);

            Entity entity = event.getRightClicked();
            List<String> quoteList = getMessages(event.getRightClicked().getUniqueId());
            Player player = event.getPlayer();
            String playerUUID = player.getUniqueId().toString();
            File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUUID + ".yml");
            if (playerYml.exists()) {
                FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
                if(playerConfig.contains("quests.civilians." + getMobID(entity.getUniqueId())+".index")){
                    Integer index = playerConfig.getInt("quests.civilians." + getMobID(entity.getUniqueId())+".index");
                    String quote = quoteList.get(index);
                    if(!quote.equals("no_comment")){
                        ChatUtils.civilianChat(player,quoteList.get(index));
                    }
                    CivilianActions.doAction(player,getMobID(entity.getUniqueId()),index);
                } else{
                    ChatUtils.civilianChat(player,quoteList.get(0));
                    CivilianActions.doAction(player,getMobID(entity.getUniqueId()),0);
                }
            } else {
                player.kickPlayer("Please contact the server developers. Your player file may be corrupt!");
            }
        }
    }

    public static void spawnCivilians(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Prison.getInstance().getDataFolder() + "/civilians/civilians.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                civilianLoader(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void civilianLoader(String s){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Prison.getInstance().getDataFolder() + "/civilians/" + s ));

            World world = Bukkit.getWorld(Prison.getInstance().getWorld());

            String uuid = br.readLine();
            String name = br.readLine();
            System.out.println("Attempting to spawn: " + name);
            System.out.println("Print line to prove I am changing shit: V.1");
            System.out.println("Raw-word-name: " + Prison.getInstance().getWorld());
            System.out.println("Spawning in world: " + world.getName());
            int profession = Integer.parseInt(br.readLine());

            List<String> locs = Arrays.asList(br.readLine().split(","));
            Location loc = new Location(world, Double.parseDouble(locs.get(0)), Double.parseDouble(locs.get(1)), Double.parseDouble(locs.get(2)));

            List<String> quotes = new ArrayList<String>();
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                quotes.add(line);
            }

            Civilian civilian = new Civilian(((CraftWorld) world).getHandle(), name, profession);
            civilian.setCustomName(ChatColor.DARK_GRAY + "~" + ChatColor.GRAY + name + ChatColor.DARK_GRAY + "~");
            civilian.setInvulnerable(true);
            civilian.setCustomNameVisible(true);

            addCivilianToList(civilian.getBukkitEntity().getUniqueId(), quotes, uuid);
            civilian.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

            ((CraftWorld) world).addEntity(civilian, CreatureSpawnEvent.SpawnReason.CUSTOM);
            System.out.println("Civilian successfully added...");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
