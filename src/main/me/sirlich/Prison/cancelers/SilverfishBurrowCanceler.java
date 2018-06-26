package main.me.sirlich.Prison.cancelers;

import main.me.sirlich.Prison.utils.PrisonUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class SilverfishBurrowCanceler implements Listener
{
    @EventHandler
    public void onSilverFishBurrow(EntityChangeBlockEvent event){
        if(!PrisonUtils.checkWorldValidity(event.getBlock().getWorld())) return;
        if(event.getEntity().getType().equals(EntityType.SILVERFISH)){
            event.setCancelled(true);
        }
    }
}
