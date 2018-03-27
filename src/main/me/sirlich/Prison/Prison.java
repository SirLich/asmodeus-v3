package main.me.sirlich.Prison;

import main.me.sirlich.Prison.cancelers.QuestItemDropCanceler;
import main.me.sirlich.Prison.civilians.Civilian;
import main.me.sirlich.Prison.civilians.CivilianHandler;
import main.me.sirlich.Prison.handlers.GateHandler;
import main.me.sirlich.Prison.cancelers.SilverfishBurrowCanceler;
import main.me.sirlich.Prison.handlers.EntityDeathHandler;
import main.me.sirlich.Prison.handlers.PlayerJoinHandler;
import main.me.sirlich.Prison.items.ItemHandler;
import main.me.sirlich.Prison.mobs.EntityHandler;
import main.me.sirlich.Prison.mobs.SpawnerHandler;
import main.me.sirlich.Prison.utils.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Prison extends JavaPlugin
{
    private static Prison instance;
    private String world;
    private int GATE_TICKER_DELAY;

    public static Prison getInstance()
    {
        return instance;
    }

    public String getWorld(){
        return world;
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new PlayerJoinHandler(), this);
        getServer().getPluginManager().registerEvents(new CivilianHandler(), this);
        getServer().getPluginManager().registerEvents(new QuestItemDropCanceler(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathHandler(), this);
        getServer().getPluginManager().registerEvents(new SilverfishBurrowCanceler(), this);
    }

    private void registerCustomMobs(){
        NMSUtils.registerEntity("civilian",NMSUtils.Type.VILLAGER, Civilian.class,false);
    }

    private void initDataFields(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Prison.getInstance().getDataFolder() + "/LichPrison"));
            this.world = br.readLine();
            System.out.println(world);
            this.GATE_TICKER_DELAY = Integer.parseInt(br.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDataFolder(){
        try {
            if (!getDataFolder().exists()) {
                System.out.println("Data folder not found... creating!");
                getDataFolder().mkdirs();
            } else {
                System.out.println("Data folder exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startGateTicker(){
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                GateHandler.runGateTick();
            }
        }, 0L, GATE_TICKER_DELAY);
    }
    @Override
    public void onEnable() {
        instance = this;
        createDataFolder();
        initDataFields();
        this.getServer().createWorld(new WorldCreator(world));
        startGateTicker();
        registerCustomMobs();
        CivilianHandler.spawnCivilians();
        ItemHandler.initRpgItems();
        EntityHandler.initRpgEntities();
        registerEvents();
        SpawnerHandler.initSpawners();
        SpawnerHandler.startSpawnersTicking();
    }
    @Override
    public void onDisable()
    {

        //Kick players
        for(Player player : Bukkit.getOnlinePlayers()){
            player.kickPlayer("LichPrison is reloading. Please login again.");
        }

        //Kill mobs
        for(Entity entity : Bukkit.getWorld(Prison.getInstance().getWorld()).getEntities()){
            entity.remove();
        }
        System.out.println("LichPrison disabled");
    }

}
