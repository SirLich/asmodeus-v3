package main.me.sirlich.Prison.items;

import de.tr7zw.itemnbtapi.NBTItem;
import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class ItemHandler
{
    public static ItemStack getItem(String key){
        File file = new File(Prison.getInstance().getDataFolder() + "/items.yml");
        FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
        return itemConfig.getItemStack(key);
    }

    public static boolean shouldBeDropped(ItemStack itemStack){
        return (getItemType(itemStack) != RpgItemType.GATE_KEY && getItemType(itemStack) != RpgItemType.QUEST_ITEM);
    }

    public static ItemStack getItem(String key, int num){
        System.out.println("Searching!");
        File file = new File(Prison.getInstance().getDataFolder() + "/items.yml");
        FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
        ItemStack itemStack = itemConfig.getItemStack(key);
        itemStack.setAmount(num);
        return itemStack;
    }

    public static RpgItemType getItemType(ItemStack itemStack){
        if(itemStack == null){
            return RpgItemType.STANDARD_ITEM;
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        if(nbtItem.hasKey("item_type")){
            return RpgItemType.valueOf(nbtItem.getString("item_type"));
        } else {
            return RpgItemType.STANDARD_ITEM;
        }
    }

    public static boolean itemsAreEqual(ItemStack input1, ItemStack input2){
        ItemStack compare1 = input1.clone();
        compare1.setAmount(1);

        ItemStack compare2 = input2.clone();
        compare2.setAmount(1);

        return(compare1.equals(compare2));
    }

    public static boolean hasItems(Player player, String itemType, int cost){
        ItemStack costStack = getItem(itemType);
        boolean hasEnough=false;
        for (ItemStack invStack : player.getInventory().getContents())
        {
            if(invStack == null){
                continue;
            }
            if (itemsAreEqual(invStack,costStack)) {

                int inv = invStack.getAmount();
                if (cost - inv > 0) {
                    cost = cost - inv;
                    System.out.println("Currently.." + cost);
                } else {
                    hasEnough=true;
                    break;
                }
            }
        }
        return hasEnough;
    }
    public static boolean consumeItems(Player player, String item, int cost, String compareType ) {
        if (!hasItems(player,item, cost)){
            return false;
        }

        ItemStack costStack = getItem(item,cost);
        //Loop though each item and consume as needed. We should of already
        //checked to make sure we had enough with CheckItems.
        for (ItemStack inventoryStack : player.getInventory().getContents())
        {
            if(inventoryStack == null){
                continue;

            }
            if (itemsAreEqual(costStack,inventoryStack)) {
                int inventoryStackAmount = inventoryStack.getAmount();
                int costStackAmount = costStack.getAmount();

                if (costStackAmount - inventoryStackAmount >= 0) {
                    costStack.setAmount(costStackAmount - inventoryStackAmount);
                    player.getInventory().remove(inventoryStack);
                } else {
                    costStack.setAmount(0);
                    inventoryStack.setAmount(inventoryStackAmount - costStackAmount);
                    break;
                }
            }
        }
        return true;
    }
}
