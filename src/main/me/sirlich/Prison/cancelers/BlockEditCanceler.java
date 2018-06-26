package main.me.sirlich.Prison.cancelers;


import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import main.me.sirlich.Prison.utils.ChatUtils;
import main.me.sirlich.Prison.utils.PrisonUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEditCanceler implements Listener
{
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event){
        if(!PrisonUtils.checkWorldValidity(event.getPlayer().getWorld())) return;
        if(event.getPlayer() != null){
            Player player = (Player) event.getPlayer();
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            if(rpgPlayer.getPlayerState() != PlayerState.GOD){
                event.setCancelled(true);
                ChatUtils.chatWarning(player,"You can't break that here!");
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
}
