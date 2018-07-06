package main.me.sirlich.Prison.civilians;

import main.me.sirlich.Prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CivilianUtils
{

    public static HashMap<UUID, String> uniqueMobIDList = new HashMap<UUID, String>();

    private static void addCivilianToList(UUID uuid, String uniqueMobID){
        uniqueMobIDList.put(uuid,uniqueMobID);
    }

    public static String getUniqueMobID(UUID uuid){
        return uniqueMobIDList.get(uuid);
    }

    /*
     * This is a public init
     */
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
        World world = Bukkit.getWorld(Prison.getInstance().getWorld());
        File civilianYML = new File(Prison.getInstance().getDataFolder() + "/civilians/" + s + ".yml");
        System.out.println("Trying to load: " + s);
        if (civilianYML.exists()){
            FileConfiguration config = YamlConfiguration.loadConfiguration(civilianYML);
            List<String> locs = Arrays.asList(config.getString("information.location").split(","));
            Location location = new Location(world, Double.parseDouble(locs.get(0)), Double.parseDouble(locs.get(1)), Double.parseDouble(locs.get(2)));

            String name = config.getString("information.name");
            Integer profession = config.getInt("information.profession");
            String uniqueMobID = config.getString("information.uuid");

            Civilian civilian = new Civilian(((CraftWorld) world).getHandle(), name, profession);
            civilian.setCustomName(ChatColor.DARK_GRAY + "~" + ChatColor.GRAY + name + ChatColor.DARK_GRAY + "~");
            civilian.setInvulnerable(true);
            civilian.setProfession(profession);
            civilian.setCustomNameVisible(true);

            addCivilianToList(civilian.getBukkitEntity().getUniqueId(), uniqueMobID);
            civilian.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

            ((CraftWorld) world).addEntity(civilian, CreatureSpawnEvent.SpawnReason.CUSTOM);
            System.out.println("Civilian successfully added...");

            try {
                config.save(civilianYML);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("WARNING! SOMETHING IS TERRIBLE WRONG IN CIVILIAN LOADER");
        }
    }
}
