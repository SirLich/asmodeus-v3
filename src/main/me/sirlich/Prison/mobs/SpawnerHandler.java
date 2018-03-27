package main.me.sirlich.Prison.mobs;

import main.me.sirlich.Prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpawnerHandler
{
    private static List<Spawner> spawners = new ArrayList<Spawner>();

    public static void initSpawners(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Prison.getInstance().getDataFolder() + "/spawners/spawners.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                spawnerLoader(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Location location = new Location(Bukkit.getWorld(Prison.getInstance().getWorld()),236,56,284);
    }

    private static void spawnerLoader(String s){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Prison.getInstance().getDataFolder() + "/spawners/" + s ));

            World world = Bukkit.getWorld(Prison.getInstance().getWorld());
            RpgEntityType rpgEntityType = RpgEntityType.valueOf(br.readLine().split(":")[0]);
            List<String> locs = Arrays.asList(br.readLine().split(":")[0].split(","));
            Location loc = new Location(world, Double.parseDouble(locs.get(0)), Double.parseDouble(locs.get(1)), Double.parseDouble(locs.get(2)));

            int radius = Integer.parseInt(br.readLine().split(":")[0]);
            int maxMobsSpawned = Integer.parseInt(br.readLine().split(":")[0]);
            int spawnAmount = Integer.parseInt(br.readLine().split(":")[0]);
            int spawnDelay = Integer.parseInt(br.readLine().split(":")[0]);
            int teleportTicker = Integer.parseInt(br.readLine().split(":")[0]);

            Spawner spawner = new Spawner(EntityHandler.getRpgEntity(rpgEntityType),loc,radius,maxMobsSpawned,spawnAmount,spawnDelay,teleportTicker);
            spawners.add(spawner);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void startSpawnersTicking(){
        for(Spawner spawner : spawners){
            spawner.doMobSpawning();
        }
    }
}
