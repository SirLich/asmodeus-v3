package main.me.sirlich.Prison.cancelers;

import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class tutorialHungerCanceler implements Listener
{
    @EventHandler
    public void playerDamagedEvent(FoodLevelChangeEvent event) {
        if(event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            if(rpgPlayer.getPlayerState().equals(PlayerState.TUTORIAL)){
                event.setCancelled(true);
            }
        }
    }
}
