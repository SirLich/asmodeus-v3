package main.me.sirlich.Prison.handlers;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.arenas.ArenaHandler;
import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class playerDamageHandler implements Listener
{
    @EventHandler
    public void playerDamagedEvent(EntityDamageEvent event) {
        if(event.isCancelled()){
            return;
        }
        //We only care about players!
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
            boolean isDead = player.getHealth()-event.getDamage() <= 0;

            //PLAYER ON PLAYER DAMAGE
            if(event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) event;
                if(edbeEvent.getDamager() instanceof Player){
                    event.setCancelled(handlePlayerDamage(player,isDead));
                }
                //MOB ON PLAYER DAMAGE
                else {
                    event.setCancelled(handleMobDamage(player,isDead));
                }
            }

            //ENVIROMENT DAMAGE
            else {
                event.setCancelled(handleEnvironmentDamage(player,isDead));
            }
        }
    }

    private boolean handlePlayerDamage(Player player, boolean isDead){
        RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
        if(rpgPlayer.getPlayerState().equals(PlayerState.PVP)){
            if(isDead){
                ArenaHandler.leaveArena(player);
                return true;
            }
        } else if(rpgPlayer.getPlayerState().equals(PlayerState.BASIC)){
            //Players in BASIC mode shoulden't be taking PVp Damage
            return true;
        } else if(rpgPlayer.getPlayerState().equals(PlayerState.GOD)){
            //Players in GOD mode shoulden't be taking PVp Damage
            return true;
        } else if(rpgPlayer.getPlayerState().equals(PlayerState.TUTORIAL)){
            //Players in TUTORIAL mode shoulden't be taking PVp Damage
            return true;
        }
        return false;
    }

    private boolean handleEnvironmentDamage(Player player, boolean isDead){
        RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
        if(rpgPlayer.getPlayerState().equals(PlayerState.PVP)){
            if(isDead){
                ArenaHandler.leaveArena(player);
                return true;
            }
        } else if(rpgPlayer.getPlayerState().equals(PlayerState.BASIC)){
            if(isDead){
                rpgPlayer.safeDropInventory();
                rpgPlayer.safeClearInventory();
                player.teleport(Prison.getInstance().getWorldSpawn());
                rpgPlayer.clearAllEffect();
                return true;
            }
        } else if(rpgPlayer.getPlayerState().equals(PlayerState.TUTORIAL)){
            if(isDead){
                ChatUtils.chatInfo(player,"Oh no! You died!");
                ChatUtils.chatInfo(player,"Don't worry though -this is just the tutorial, we saved your items for you.");
                player.teleport(Prison.getInstance().getWorldSpawn());
                rpgPlayer.clearAllEffect();
                return true;
            }
        }
        return false;
    }

    private boolean handleMobDamage(Player player, boolean isDead){
        RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
        if(rpgPlayer.getPlayerState().equals(PlayerState.PVP)){
            if(isDead){
                ArenaHandler.leaveArena(player);
                return true;
            }
        } else if(rpgPlayer.getPlayerState().equals(PlayerState.BASIC)){
            if(isDead){
                rpgPlayer.safeDropInventory();
                rpgPlayer.safeClearInventory();
                player.teleport(Prison.getInstance().getWorldSpawn());
                rpgPlayer.clearAllEffect();
                return true;
            }
        } else if(rpgPlayer.getPlayerState().equals(PlayerState.TUTORIAL)){
            if(isDead){
                ChatUtils.chatInfo(player,"Oh no! You died!");
                ChatUtils.chatInfo(player,"Don't worry though -this is just the tutorial, we saved your items for you.");
                player.teleport(Prison.getInstance().getWorldSpawn());
                rpgPlayer.clearAllEffect();
                return true;
            }
        }
        return false;
    }
}
