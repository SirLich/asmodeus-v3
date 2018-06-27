package main.me.sirlich.Prison.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.entity.Player;
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

    public static void initRpgItems(){
        //Keys
        createItem(Material.EYE_OF_ENDER, generateKeyName("white crystal"), RpgItemType.WHITE_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("orange crystal"), RpgItemType.ORANGE_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("magenta crystal"), RpgItemType.MAGENTA_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("light-blue crystal"), RpgItemType.LIGHT_BLUE_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("yellow crystal"), RpgItemType.YELLOW_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("lime crystal"), RpgItemType.LIME_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("pink crystal"), RpgItemType.PINK_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("gray crystal"), RpgItemType.GRAY_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("light-gray crystal"), RpgItemType.LIGHT_GRAY_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("cyan crystal"), RpgItemType.CYAN_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("blue crystal"), RpgItemType.BLUE_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("brown crystal"), RpgItemType.BROWN_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("green crystal"), RpgItemType.GREEN_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("red crystal"), RpgItemType.RED_GLASS_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("black crystal"), RpgItemType.BLACK_GLASS_KEY, RpgItemCategory.GATE_KEY,true);

        createItem(Material.EYE_OF_ENDER, generateKeyName("white concrete"), RpgItemType.WHITE_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("orange concrete"), RpgItemType.ORANGE_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("magenta concrete"), RpgItemType.MAGENTA_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("light-blue concrete"), RpgItemType.LIGHT_BLUE_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("yellow concrete"), RpgItemType.YELLOW_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("lime concrete"), RpgItemType.LIME_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("pink concrete"), RpgItemType.PINK_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("gray concrete"), RpgItemType.GRAY_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("light-gray concrete"), RpgItemType.LIGHT_GRAY_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("cyan concrete"), RpgItemType.CYAN_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("blue concrete"), RpgItemType.BLUE_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("brown concrete"), RpgItemType.BROWN_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("green concrete"), RpgItemType.GREEN_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("red concrete"), RpgItemType.RED_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);
        createItem(Material.EYE_OF_ENDER, generateKeyName("black concrete"), RpgItemType.BLACK_CONCRETE_KEY, RpgItemCategory.GATE_KEY,true);


        //Quest Items
        createItem(Material.STONE_SWORD, "Starter Sword", RpgItemType.STARTER_SWORD, RpgItemCategory.QUEST_ITEM);

        //Resources
        createItem(Material.BONE,"Rat Bone", RpgItemType.RAT_BONE, RpgItemCategory.RESOURCE);
        createItem(Material.ROTTEN_FLESH, "Rat Meat", RpgItemType.RAT_MEAT, RpgItemCategory.RESOURCE);
        createItem(Material.GOLD_NUGGET, "Gold Ring", RpgItemType.GOLD_RING, RpgItemCategory.RESOURCE);
    }


    public static String generateKeyName(String name){
        return ChatColor.LIGHT_PURPLE + "Key Stone: " + ChatColor.WHITE +  name;
    }


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
        itemStack.setAmount(1);
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

    private static ItemStack createCustomItem(Material material, String name, RpgItemType type, RpgItemCategory category, Boolean customColor){
        ItemStack itemStack = new ItemStack(material,1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lorelines = new ArrayList<String>();
        itemMeta.setDisplayName(name);
        if(category == RpgItemCategory.QUEST_ITEM){
            lorelines.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Quest Item");
            if(!customColor){
                itemMeta.setDisplayName(ChatColor.DARK_PURPLE + name);
            }
        } else if(category == RpgItemCategory.GATE_KEY){
            lorelines.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Gate Key");
            if(!customColor){
                itemMeta.setDisplayName(ChatColor.AQUA + name);
            }
        } else if(category == RpgItemCategory.RESOURCE){
            lorelines.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Resource");
            if(!customColor){
                itemMeta.setDisplayName(ChatColor.WHITE + name);
            }
        }
        itemMeta.setLore(lorelines);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static void createItem(Material material, String name, RpgItemType type, RpgItemCategory category){
        ItemStack itemStack = createCustomItem(material,name,type,category, false);
        saveItem(type, itemStack);
    }

    private static void createItem(Material material, String name, RpgItemType type, RpgItemCategory category, Boolean customColor){
        ItemStack itemStack = createCustomItem(material,name,type,category, customColor);
        saveItem(type, itemStack);
    }


    private static void saveItem(RpgItemType type, ItemStack itemStack){
        itemsMap.put(type, itemStack);
        rpgItemsMap.put(itemStack,type);
    }

    public static boolean hasItems(Player player, ItemStack costStack){
        //make sure we have enough
        int cost = costStack.getAmount();
        System.out.println("Looking for " + cost);
        boolean hasEnough=false;
        for (ItemStack invStack : player.getInventory().getContents())
        {
            if(invStack == null)
                continue;
            if (invStack.getTypeId() == costStack.getTypeId()) {

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
    public static boolean consumeItems(Player player, ItemStack costStack) {
        if (!hasItems(player,costStack)){
            return false;
        }
        //Loop though each item and consume as needed. We should of already
        //checked to make sure we had enough with CheckItems.
        for (ItemStack invStack : player.getInventory().getContents())
        {
            if(invStack == null)
                continue;

            if (invStack.getTypeId() == costStack.getTypeId()) {
                int inv = invStack.getAmount();
                int cost = costStack.getAmount();
                if (cost - inv >= 0) {
                    costStack.setAmount(cost - inv);
                    player.getInventory().remove(invStack);
                } else {
                    costStack.setAmount(0);
                    invStack.setAmount(inv - cost);
                    break;
                }
            }
        }
        return true;
    }
}
