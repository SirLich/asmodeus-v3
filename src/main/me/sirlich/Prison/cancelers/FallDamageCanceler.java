package main.me.sirlich.Prison.cancelers;

import main.me.sirlich.Prison.zones.ZoneHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallDamageCanceler implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageEvent(EntityDamageEvent event){
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if(ZoneHandler.playerInsideZoneTag(player,"no_fall_damage")){
                event.setCancelled(true);
            }
        }
    }
}
