package main.me.sirlich.Prison;

import main.me.sirlich.Prison.arenas.ArenaHandler;
import main.me.sirlich.Prison.cancelers.QuestItemDropCanceler;
import main.me.sirlich.Prison.cancelers.BlockEditCanceler;
import main.me.sirlich.Prison.civilians.Civilian;
import main.me.sirlich.Prison.civilians.CivilianHandler;
import main.me.sirlich.Prison.civilians.CivilianUtils;
import main.me.sirlich.Prison.commands.ResetPlayerFile;
import main.me.sirlich.Prison.commands.SetPlayerState;
import main.me.sirlich.Prison.handlers.playerDamageHandler;
import main.me.sirlich.Prison.items.ItemCommand;
import main.me.sirlich.Prison.zones.ZoneCreator;
import main.me.sirlich.Prison.gates.GateHandler;
import main.me.sirlich.Prison.cancelers.SilverfishBurrowCanceler;
import main.me.sirlich.Prison.handlers.EntityDeathHandler;
import main.me.sirlich.Prison.handlers.PlayerJoinHandler;
import main.me.sirlich.Prison.handlers.PlayerLeaveHandler;
import main.me.sirlich.Prison.mobs.EntityHandler;
import main.me.sirlich.Prison.mobs.SpawnerHandler;
import main.me.sirlich.Prison.utils.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Prison extends JavaPlugin
{
    private static Prison instance;
    private String world;
    private int GATE_TICKER_DELAY;
    private Location WORLD_SPAWN;

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
        getServer().getPluginManager().registerEvents(new QuestItemDropCanceler(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathHandler(), this);
        getServer().getPluginManager().registerEvents(new SilverfishBurrowCanceler(), this);
        getServer().getPluginManager().registerEvents(new BlockEditCanceler(),this);
        getServer().getPluginManager().registerEvents(new GateHandler(),this);
        getServer().getPluginManager().registerEvents(new ZoneCreator(),this);
        getServer().getPluginManager().registerEvents(new playerDamageHandler(), this);

    }

    private void registerCommands(){
        this.getCommand("cleardata").setExecutor(new ResetPlayerFile());
        this.getCommand("set").setExecutor(new SetPlayerState());
        this.getCommand("zone").setExecutor(new ZoneCreator());
        this.getCommand("it").setExecutor(new ItemCommand());

    }
    private void registerCustomMobs(){
        NMSUtils.registerEntity("civilian",NMSUtils.Type.VILLAGER, Civilian.class,false);
    }

    public Location getWorldSpawn(){
        return WORLD_SPAWN;
    }
    private void initDataFields(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Prison.getInstance().getDataFolder() + "/LichPrison"));
            this.world = br.readLine();
            this.GATE_TICKER_DELAY = Integer.parseInt(br.readLine());
            List<String> locs = Arrays.asList(br.readLine().split(","));
            this.WORLD_SPAWN = new Location(Bukkit.getWorld(world), Double.parseDouble(locs.get(0)), Double.parseDouble(locs.get(1)), Double.parseDouble(locs.get(2)));

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

    private void startArenaTicker(){
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                ArenaHandler.runArenaTick();
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
