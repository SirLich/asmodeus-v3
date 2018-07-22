package main.me.sirlich.Prison.cancelers;


import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import main.me.sirlich.Prison.resources.ResourceUtils;
import main.me.sirlich.Prison.utils.ChatUtils;
import main.me.sirlich.Prison.utils.PrisonUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

public class BlockEditCanceler implements Listener
{
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event){
        if(!PrisonUtils.checkWorldValidity(event.getPlayer().getWorld())) return;

        final Material block = event.getBlock().getType();
        final World world = event.getBlock().getWorld();
        final Location location = event.getBlock().getLocation();

        if(event.getPlayer() != null){
            Player player = (Player) event.getPlayer();
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            if(rpgPlayer.getPlayerState() != PlayerState.GOD){
                //BLOCK IS RESOURCE
                if(block.equals(Material.COAL_ORE) ||
                        block.equals(Material.IRON_ORE) ||
                        block.equals(Material.GOLD_ORE) ||
                        block.equals(Material.DIAMOND_ORE) ||
                        block.equals(Material.REDSTONE_ORE) ||
                        block.equals(Material.LAPIS_ORE) ||
                        block.equals(Material.EMERALD_ORE)
                        ){
                    System.out.println("1");
                    event.setCancelled(true);
                    world.getBlockAt(location).setType(Material.STONE);
                    ResourceUtils.handleDrop(world,location,block);
                    BukkitScheduler scheduler = Prison.getInstance().getServer().getScheduler();
                    scheduler.scheduleSyncDelayedTask(Prison.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            world.getBlockAt(location).setType(block);
                        }
                    }, ResourceUtils.getRespawnTime(block));
                } else {
                    event.setCancelled(true);
                    ChatUtils.chatWarning(player,"You can't break that here!");
                }
            }
        }
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event){
        if(!PrisonUtils.checkWorldValidity(event.getPlayer().getWorld())) return;
        if(event.getPlayer() != null){
            Player player = (Player) event.getPlayer();
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            if(rpgPlayer.getPlayerState() != PlayerState.GOD){
                event.setCancelled(true);
                ChatUtils.chatWarning(player,"You can't place that here!");
            }
        }
    }

    @EventHandler
    public void placeWaterEvent(PlayerBucketEmptyEvent event){
        if(!PrisonUtils.checkWorldValidity(event.getPlayer().getWorld())) return;
        if(event.getPlayer() != null){
            Player player = (Player) event.getPlayer();
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            if(rpgPlayer.getPlayerState() != PlayerState.GOD){
                event.setCancelled(true);
                ChatUtils.chatWarning(player,"You can't do that here!");
            }
        }
    }

    @EventHandler
    public void takeWaterEvent(PlayerBucketFillEvent event){
        if(!PrisonUtils.checkWorldValidity(event.getPlayer().getWorld())) return;
        if(event.getPlayer() != null){
            Player player = (Player) event.getPlayer();
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            if(rpgPlayer.getPlayerState() != PlayerState.GOD){
                event.setCancelled(true);
                ChatUtils.chatWarning(player,"You can't do that here!");
            }
        }
    }
}
