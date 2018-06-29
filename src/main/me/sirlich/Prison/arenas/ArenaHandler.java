package main.me.sirlich.Prison.arenas;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import main.me.sirlich.Prison.items.ItemHandler;
import main.me.sirlich.Prison.zones.ZoneHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;

public class ArenaHandler
{

    public static ArenaType getArenaType(String arena){
        File arenaYml = new File(Prison.getInstance().getDataFolder() + "/arenas.yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(arenaYml);
        return ArenaType.valueOf(arenaConfig.getString(arena + ".type"));
    }

    public static void runArenaTick(){
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(player.isSneaking() && player.getLocation().add(new Vector(0,-1,0)).getBlock().getType().equals(Material.BARRIER)){
                if(ZoneHandler.playerInsideZoneTag(player,"pvp_arena")) {
                    File arenaYml = new File(Prison.getInstance().getDataFolder() + "/arenas.yml");
                    FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(arenaYml);

                    RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
                    rpgPlayer.setPlayerState(PlayerState.PVP);
                    String pvpArena = ZoneHandler.getPvpZone(player);
                    ArenaType arenaType = ArenaHandler.getArenaType(pvpArena);

                    if (arenaType.equals(ArenaType.KIT)) {
                        rpgPlayer.clearAllEffect();

                        rpgPlayer.savePlayerInventory();
                        player.getInventory().clear();

                        player.getInventory().setHelmet(ItemHandler.getItem(arenaConfig.getString(pvpArena + ".items.helmet")));
                        player.getInventory().setChestplate(ItemHandler.getItem(arenaConfig.getString(pvpArena + ".items.chest_plate")));
                        player.getInventory().setLeggings(ItemHandler.getItem(arenaConfig.getString(pvpArena + ".items.leggings")));
                        player.getInventory().setBoots(ItemHandler.getItem(arenaConfig.getString(pvpArena + ".items.boots")));
                        for (String itemCode : arenaConfig.getStringList(pvpArena + ".items.inventory")) {
                            System.out.println(itemCode);
                            String[] itemCodes = itemCode.split("\\s+");
                            System.out.println(itemCodes[0]);
                            System.out.println(itemCodes[1]);
                            player.getInventory().addItem(ItemHandler.getItem(itemCodes[0].trim(), Integer.parseInt(itemCodes[1])));
                        }
                    } else if(arenaType.equals(ArenaType.OP)){
                        System.out.println("Nothing happens here yet!");
                    }
                    Location down = new Location(player.getWorld(), 0,-2,0);
                    player.teleport(player.getLocation().add(down));
                }
            }
        }
    }
}
