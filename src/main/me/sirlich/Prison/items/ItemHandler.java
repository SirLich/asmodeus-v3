package main.me.sirlich.Prison.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemHandler
{
    private static HashMap<RpgItemType, ItemStack> itemsMap = new HashMap<main.me.sirlich.Prison.items.RpgItemType,ItemStack>();
    private static HashMap<ItemStack, RpgItemType> rpgItemsMap = new HashMap<ItemStack, RpgItemType>();

    public static ItemStack getItem(RpgItemType key){
        ItemStack itemStack = new ItemStack(itemsMap.get(key));
        return itemStack;
    }

    public static ItemStack getItem(RpgItemType key, int num){
        ItemStack itemStack = new ItemStack(itemsMap.get(key));
        itemStack.setAmount(num);
        return itemStack;
    }

    public static RpgItemType getItemType(ItemStack itemStack){
        return rpgItemsMap.get(itemStack);
    }


    public static RpgItemCategory getItemCategory(ItemStack itemStack){
        String lore = itemStack.getItemMeta().getLore().get(0);
        if(lore.contains("Quest Item")){
            return RpgItemCategory.QUEST_ITEM;
        } else if(lore.contains("Gate Key")){
            return RpgItemCategory.GATE_KEY;
        } else if(lore.contains("Resource")){
            return RpgItemCategory.RESOURCE;
        } else if(lore.contains("Relic")){
            return RpgItemCategory.RELIC;
        } else {
            return RpgItemCategory.NULL;
        }
    }


    private static ItemStack createCustomItem(Material material, String name, RpgItemType type, RpgItemCategory category){
        ItemStack itemStack = new ItemStack(material,1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lorelines = new ArrayList<String>();
        itemMeta.setDisplayName(name);
        if(category == RpgItemCategory.QUEST_ITEM){
            lorelines.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Quest Item");
            itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + name);
        } else if(category == RpgItemCategory.GATE_KEY){
            itemMeta.setDisplayName(ChatColor.AQUA + name);
            lorelines.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Gate Key");
        } else if(category == RpgItemCategory.RESOURCE){
            lorelines.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Resource");
        }
        itemMeta.setLore(lorelines);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static void createItem(Material material, String name, RpgItemType type, RpgItemCategory category){
        ItemStack itemStack = createCustomItem(material,name,type,category);
        saveItem(type, itemStack);
    }

    private static void saveItem(RpgItemType type, ItemStack itemStack){
        itemsMap.put(type, itemStack);
        rpgItemsMap.put(itemStack,type);
    }

    public static void initRpgItems(){
        //Keys
        createItem(Material.GHAST_TEAR,"Red-Crystal Key", RpgItemType.RED_CRYSTAL_KEY, RpgItemCategory.GATE_KEY);

        //Quest Items
        createItem(Material.STONE_SWORD, "Starter Sword", RpgItemType.STARTER_SWORD, RpgItemCategory.QUEST_ITEM);

        //Resources
        createItem(Material.BONE,"Rat Bone", RpgItemType.RAT_BONE, RpgItemCategory.RESOURCE);
        createItem(Material.ROTTEN_FLESH, "Rat Meat", RpgItemType.RAT_MEAT, RpgItemCategory.RESOURCE);
        createItem(Material.GOLD_NUGGET, "Gold Ring", RpgItemType.GOLD_RING, RpgItemCategory.RESOURCE);
    }
}
