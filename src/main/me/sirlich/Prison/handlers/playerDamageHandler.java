package main.me.sirlich.Prison.handlers;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.arenas.ArenaHandler;
import main.me.sirlich.Prison.arenas.ArenaType;
import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import main.me.sirlich.Prison.zones.ZoneHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;

public class playerDamageHandler implements Listener
{
    @EventHandler
    public void playerDamagedEvent(EntityDamageEvent event) {
        //We only care about players!
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);

            //Handle pvp pit deaths!
            if(rpgPlayer.getPlayerState().equals(PlayerState.PVP)){
                if((player.getHealth()-event.getDamage()) <= 0) {
                    event.setCancelled(true);
                    player.setHealth(20);
                    rpgPlayer.clearAllEffect();
                    String arena = ZoneHandler.getPvpZone(player);
                    ArenaType arenaType = ArenaHandler.getArenaType(arena);

                    if(arenaType.equals(ArenaType.KIT)){
                        rpgPlayer.safeClearInventory();
                        rpgPlayer.safeReturnInventory();
                    } else if(arenaType.equals(ArenaType.OP)){
                        rpgPlayer.safeDropInventory();
                    }

                    File arenaYml = new File(Prison.getInstance().getDataFolder() + "/arenas.yml");
                    FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(arenaYml);

                    int x = arenaConfig.getInt(arena + ".respawn_location.x");
                    int y = arenaConfig.getInt(arena + ".respawn_location.y");
                    int z = arenaConfig.getInt(arena + ".respawn_location.z");

                    Location respawnLocation = new Location(Bukkit.getWorld(Prison.getInstance().getWorld()), x,y,z);
                    player.teleport(respawnLocation);
                }
            } else if(rpgPlayer.getPlayerState().equals(PlayerState.BASIC)){
                //No PVP unless in a PVP zone!!!
                event.setCancelled(true);
            } else if(rpgPlayer.getPlayerState().equals(PlayerState.GOD)){
                event.setCancelled(true);
            }
        }
    }
}
