package main.me.sirlich.Prison.items;

import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemHandler
{
    private static HashMap<RpgItemType, ItemStack> itemsMap = new HashMap<RpgItemType,ItemStack>();

    public static ItemStack getItem(RpgItemType key){
        ItemStack itemStack = new ItemStack(itemsMap.get(key));
        return itemStack;
    }

    public static ItemStack getItem(RpgItemType key, int num){
        ItemStack itemStack = new ItemStack(itemsMap.get(key));
        itemStack.setAmount(5);
        return itemStack;
    }

    public boolean itemIs(ItemStack itemStack, RpgItemType key){
        return itemsMap.get(key).equals(itemStack);
    }

    private static void createItem(Material material, String name, RpgItemType rpgItemType){
        ItemStack itemStack = new ItemStack(material,1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lorelines = new ArrayList<String>();
        lorelines.add(ChatColor.GRAY + "Quest Item");
        itemMeta.setLore(lorelines);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + name);
        itemStack.setItemMeta(itemMeta);
        itemsMap.put(rpgItemType, itemStack);
    }

    public static void initRpgItems(){
        createItem(Material.BONE,"Rat bone", RpgItemType.RAT_BONE);
        createItem(Material.ROTTEN_FLESH, "Rat meat", RpgItemType.RAT_MEAT);
        createItem(Material.GOLD_NUGGET, "Gold ring", RpgItemType.GOLD_RING);
        createItem(Material.STONE_SWORD, "Starter sword", RpgItemType.STARTER_SWORD);
    }
}
