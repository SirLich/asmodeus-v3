package main.me.sirlich.Prison.mobs;


import main.me.sirlich.Prison.Prison;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;


public class Spawner
{
    public int getNumberOfLivingMobs()
    {
        return numberOfLivingMobs;
    }

    public void setNumberOfLivingMobs(int numberOfLivingMobs)
    {
        this.numberOfLivingMobs = numberOfLivingMobs;
    }

    public void decreaseNumberOfLivingMobs(){
        numberOfLivingMobs --;
    }
    private int numberOfLivingMobs;

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public int getTeleportTicker()
    {
        return teleportTicker;
    }

    public void setTeleportTicker(int teleportTicker)
    {
        this.teleportTicker = teleportTicker;
    }

    private Location location;
    private CloneableEntity rpgEntity;

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    private int radius;
    private int maxMobsSpawned;
    private int spawnAmount;
    private int spawnDelay;
    private int teleportTicker;

    /*TODO:
     *   Make mob spawning use the Radius
     *   Make the teleport ticker do something
     */

    public void doMobSpawning(){
        if(numberOfLivingMobs < maxMobsSpawned){
            for(int i = 0; i < spawnAmount; i ++){
                rpgEntity.spawnClone(location, this);
                numberOfLivingMobs +=1;
            }
        }
        new BukkitRunnable() {
            public void run() {
                doMobSpawning();
            }

        }.runTaskLater(Prison.getInstance(), spawnDelay);
    }

    public Spawner(CloneableEntity rpgEntity,
                   Location location,
                   int radius,
                   int maxMobsSpawned,
                   int spawnAmount,
                   int spawnDelay,
                   int teleportTicker){
        numberOfLivingMobs = 0;
        this.rpgEntity = rpgEntity;
        this.location = location;
        this.radius = radius;
        this.maxMobsSpawned = maxMobsSpawned;
        this.spawnAmount = spawnAmount;
        this.spawnDelay = spawnDelay;
        this.teleportTicker = teleportTicker;
    }
}
