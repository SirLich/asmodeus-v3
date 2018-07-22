package main.me.sirlich.Prison;

import main.me.sirlich.Prison.arenas.ArenaHandler;
import main.me.sirlich.Prison.cancelers.*;
import main.me.sirlich.Prison.civilians.Civilian;
import main.me.sirlich.Prison.civilians.CivilianHandler;
import main.me.sirlich.Prison.civilians.CivilianUtils;
import main.me.sirlich.Prison.core.CleardataCommand;
import main.me.sirlich.Prison.core.StateCommand;
import main.me.sirlich.Prison.handlers.playerDamageHandler;
import main.me.sirlich.Prison.items.ItCommand;
import main.me.sirlich.Prison.resources.ResourceUtils;
import main.me.sirlich.Prison.zones.ZoneCreator;
import main.me.sirlich.Prison.gates.GateHandler;
import main.me.sirlich.Prison.handlers.EntityDeathHandler;
import main.me.sirlich.Prison.handlers.PlayerJoinHandler;
import main.me.sirlich.Prison.handlers.PlayerLeaveHandler;
import main.me.sirlich.Prison.mobs.EntityHandler;
import main.me.sirlich.Prison.mobs.SpawnerHandler;
import main.me.sirlich.Prison.utils.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.*;

public class Prison extends JavaPlugin
{
    private static Prison instance;
    private String world;
    private int GATE_TICKER_DELAY;
    private int ARENA_TICKER_DELAY;
    private Location WORLD_SPAWN;

    public Sound getGateSound()
    {
        return GATE_SOUND;
    }

    private Sound GATE_SOUND;

    public static Prison getInstance()
    {
        return instance;
    }

    public String getWorld(){
        return world;
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new PlayerJoinHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveHandler(), this);
        getServer().getPluginManager().registerEvents(new CivilianHandler(), this);
        getServer().getPluginManager().registerEvents(new ItemDropCanceler(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathHandler(), this);
        getServer().getPluginManager().registerEvents(new SilverfishBurrowCanceler(), this);
        getServer().getPluginManager().registerEvents(new BlockEditCanceler(),this);
        getServer().getPluginManager().registerEvents(new GateHandler(),this);
        getServer().getPluginManager().registerEvents(new ZoneCreator(),this);
        getServer().getPluginManager().registerEvents(new playerDamageHandler(), this);
        getServer().getPluginManager().registerEvents(new tutorialHungerCanceler(),this);
        getServer().getPluginManager().registerEvents(new FallDamageCanceler(),this);
    }

    private void registerCommands(){
        this.getCommand("cleardata").setExecutor(new CleardataCommand());
        this.getCommand("state").setExecutor(new StateCommand());
        this.getCommand("zone").setExecutor(new ZoneCreator());
        this.getCommand("it").setExecutor(new ItCommand());

    }
    private void registerCustomMobs(){
        NMSUtils.registerEntity("civilian",NMSUtils.Type.VILLAGER, Civilian.class,false);
    }

    public Location getWorldSpawn(){
        return WORLD_SPAWN;
    }


    private void initDataFields(){

        File arenaYml = new File(Prison.getInstance().getDataFolder() + "/prison.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(arenaYml);

        this.world = config.getString("world");
        this.GATE_TICKER_DELAY = config.getInt("tickers.gate");
        this.ARENA_TICKER_DELAY = config.getInt("tickers.arena");
        this.GATE_SOUND = Sound.valueOf(config.getString("gate_sound"));
        this.WORLD_SPAWN = new Location(Bukkit.getWorld(world),config.getDouble("spawn_location.x"), config.getDouble("spawn_location.y"),config.getDouble("spawn_location.z"),Float.parseFloat(config.getString("spawn_location.pitch")),Float.parseFloat(config.getString("spawn_location.yaw")));

    }

    private void createDataFolder(){
        try {
            if (!getDataFolder().exists()) {
                System.out.println("Data folder not found... creating!");
                getDataFolder().mkdirs();
                System.out.println(getDataFolder());
            } else {
                System.out.println("Data folder exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startGateTicker(){
        System.out.println("Gate Ticker Started");
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                GateHandler.runGateTick();
            }
        }, 0L, GATE_TICKER_DELAY);
    }

    private void startArenaTicker(){
        System.out.println("Start Ticker Started");
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                ArenaHandler.runArenaTick();
            }
        }, 0L, ARENA_TICKER_DELAY);
    }
    @Override
    public void onEnable() {
        System.out.println("=-=-=-=-=-=-=-=-= LichPrison =-=-=-=-=-=-= ");
        instance = this;
        createDataFolder();
        initDataFields();
        this.getServer().createWorld(new WorldCreator(world));
        startGateTicker();
        startArenaTicker();
        registerCustomMobs();
        registerCommands();
        CivilianUtils.spawnCivilians();
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
