package main.me.sirlich.Prison.cancelers;

import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;


public class QuestItemDropCanceler implements Listener
{
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        if(event.getItemDrop().getItemStack().getItemMeta().getLore().contains("Quest Item")){
            ChatUtils.chatWarning(event.getPlayer(),"Quest items cannot be dropped!");
        }
        event.setCancelled(true);
    }
}
