package main.me.sirlich.Prison.handlers;

import de.tr7zw.itemnbtapi.NBTItem;
import main.me.sirlich.Prison.Prison;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


public class AbilityHandler implements Listener
{
    @EventHandler
    public void onItemClick(PlayerInteractEvent event){
        Player player = (Player) event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(event.getItem() != null){
                ItemStack itemStack = event.getItem();
                NBTItem nbtItem = new NBTItem(itemStack);
                if(nbtItem.hasKey("ability_type")){
                    String type = nbtItem.getString("ability_type");
                    int level = 1;
                    if(nbtItem.hasKey("ability_level")){
                        level = nbtItem.getInteger("ability_level");
                    }
                    handlerAbility(player,type,level);
                }
            }
        }
    }

    private void handlerAbility(final Player player, final String ability, int level){
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(ability.equalsIgnoreCase("hyperspeed")){
            itemStack.setAmount(itemStack.getAmount() - 1);
            player.setWalkSpeed(1);
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    player.setWalkSpeed(0.1f);
                }

            }.runTaskLater(Prison.getInstance(), 60);
        }
    }
}
