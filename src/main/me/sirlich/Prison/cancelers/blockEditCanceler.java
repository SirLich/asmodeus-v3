package main.me.sirlich.Prison.cancelers;


import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class blockEditCanceler implements Listener
{
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event){
        if(event.getPlayer() != null){
            Player player = (Player) event.getPlayer();
            if(player.getGameMode().equals(GameMode.SURVIVAL)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event){
        if(event.getPlayer() != null){
            Player player = (Player) event.getPlayer();
            if(player.getGameMode().equals(GameMode.SURVIVAL)){
                event.setCancelled(true);
            }
        }
    }
}
