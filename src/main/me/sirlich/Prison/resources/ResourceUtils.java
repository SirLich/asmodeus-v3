package main.me.sirlich.Prison.resources;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.items.ItemHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class ResourceUtils
{
    public static void handleDrop(World world, Location location, Material material){
        File arenaYml = new File(Prison.getInstance().getDataFolder() + "/prison.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(arenaYml);
        String mat = material.toString().replace("_ORE","").toLowerCase();
        System.out.println(mat);
        for(int i = 0; i < config.getInt("resources." + mat + ".drop_amount"); i ++){
            double dropChance = Math.random();
            if(dropChance < config.getDouble("resources." + mat + ".drop_chance")){
                ItemStack itemStack = ItemHandler.getItem(config.getString("resources." + mat + ".drop_item"));
                world.dropItem(location,itemStack);
            }
        }
    }
    public static int getRespawnTime(Material material){
        File arenaYml = new File(Prison.getInstance().getDataFolder() + "/prison.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(arenaYml);
        return config.getInt("resources." + material.toString().replace("_ORE","").toLowerCase() + ".respawn_time");
    }
}
