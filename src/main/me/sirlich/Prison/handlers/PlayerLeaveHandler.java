package main.me.sirlich.Prison.handlers;

import main.me.sirlich.Prison.arenas.ArenaHandler;
import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveHandler implements Listener
{
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
        PlayerState playerState = rpgPlayer.getPlayerState();
        if(playerState.equals(PlayerState.TUTORIAL)){
            rpgPlayer.clearPlayerFile();
        } else if(playerState.equals(PlayerState.PVP)){
            ArenaHandler.leaveArena(player);
        }
        RpgPlayerList.removePlayer(player);
    }
}
