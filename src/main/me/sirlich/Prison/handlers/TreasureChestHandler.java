package main.me.sirlich.Prison.handlers;

import de.tr7zw.itemnbtapi.NBTItem;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TreasureChestHandler implements Listener
{
    @EventHandler
    public void inventoryOpenEvent(InventoryOpenEvent event)
    {
        if(event.getInventory().getTitle().equalsIgnoreCase("container.chest")){
            Player player = (Player) event.getPlayer();
            String uuid = player.getUniqueId().toString();
            System.out.println("Its a chest!");

            boolean opened = false;
            ItemStack paper = null;
            for(ItemStack itemStack : event.getInventory().getContents()){
                if(itemStack != null && itemStack.getItemMeta().getDisplayName().contains("TreasureChest")){
                    System.out.println("Treasure chest!");
                    event.setCancelled(true);
                    NBTItem nbtItem = new NBTItem(itemStack);
                    paper = itemStack.clone();
                    for(String key : nbtItem.getKeys()){
                        if(key.equalsIgnoreCase(uuid)){
                            opened = true;
                            break;
                        }
                    }
                }
            }
            if(opened){
                Inventory chestContents = Bukkit.createInventory(null, 64, "Treasure Chest");
                event.getPlayer().openInventory(chestContents);
            } else {
                Inventory chestContents = Bukkit.createInventory(null, 64, "Treasure Chest");
                chestContents.addItem(event.getInventory().getContents());
                chestContents.remove(paper);
                event.getPlayer().openInventory(chestContents);
            }
        } else {
            System.out.println("NOT A CHEST!");
            System.out.println(event.getInventory().getTitle());
        }
    }
}
