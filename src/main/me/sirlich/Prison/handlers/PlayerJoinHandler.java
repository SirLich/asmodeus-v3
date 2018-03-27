package main.me.sirlich.Prison.handlers;

import main.me.sirlich.Prison.Prison;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class PlayerJoinHandler implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerUuid = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUuid + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
            //Prison.getInstance().saveResource(Prison.getInstance().getDataFolder() + "/players/" + playerUuid + ".yml",false);
            String oldPlayerName = playerConfig.getString("basics.name");

            if(oldPlayerName != player.getName()) {
                playerConfig.set("basics.name", player.getName());
            }
            Integer playerConfigJoins = playerConfig.getInt("basics.timesjoined");
            ++playerConfigJoins;
            playerConfig.set("basics.timesjoined", playerConfigJoins);
            try {
                playerConfig.save(playerYml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Attempting to create player-file...");
            if(createPlayerYml(player, playerYml, true)) {
                System.out.println("Created YML file for: " + player.getName());
            } else {
                System.out.println("Failed to create YML file for: " + player.getName());
            }
        }
    }

    public Boolean createPlayerYml(Player player, File playerYml, Boolean online) {
        try {
            playerYml.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
        playerConfig.set("basics.name", player.getName());
        playerConfig.set("basics.timesjoined", 0);
        playerConfig.set("gates.0",false);
        playerConfig.set("gates.1",false);
        playerConfig.set("gates.2",false);
        playerConfig.set("gates.3",false);
        playerConfig.set("gates.4",false);
        playerConfig.set("gates.5",false);
        playerConfig.set("gates.6",false);
        playerConfig.set("gates.7",false);
        playerConfig.set("gates.8",false);
        playerConfig.set("gates.9",false);
        playerConfig.set("gates.11",false);
        playerConfig.set("gates.12",false);
        playerConfig.set("gates.13",false);
        playerConfig.set("gates.14",false);
        playerConfig.set("gates.15",false);
        playerConfig.set("gates.16",false);
        playerConfig.set("gates.17",false);
        playerConfig.set("gates.18",false);


        try {
            playerConfig.save(playerYml);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
