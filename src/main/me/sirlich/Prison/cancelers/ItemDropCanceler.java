package main.me.sirlich.Prison.cancelers;

import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import main.me.sirlich.Prison.items.ItemHandler;
import main.me.sirlich.Prison.items.RpgItemType;
import main.me.sirlich.Prison.utils.ChatUtils;
import main.me.sirlich.Prison.utils.PrisonUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;


public class ItemDropCanceler implements Listener
{
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        if(!PrisonUtils.checkWorldValidity(event.getPlayer().getWorld())) return;
        Player player = (Player) event.getPlayer();
        RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
        if(rpgPlayer.getPlayerState() != PlayerState.GOD){
            if(ItemHandler.getItemType(event.getItemDrop().getItemStack()).equals(RpgItemType.QUEST_ITEM)){
                ChatUtils.chatWarning(event.getPlayer(),"Quest items cannot be dropped!");
                event.setCancelled(true);
            } else if(ItemHandler.getItemType(event.getItemDrop().getItemStack()).equals(RpgItemType.GATE_KEY)){
                ChatUtils.chatWarning(event.getPlayer(),"Gate keys cannot be dropped!");
                event.setCancelled(true);
            }
        }
    }
}
