package main.me.sirlich.Prison.items;

import de.tr7zw.itemnbtapi.NBTItem;
import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;

public class ItCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length < 1){
                ChatUtils.chatCommand(player,"list, give, save, delete, tag, tags, rtag");
                return true;
            }
            if(args[0].equalsIgnoreCase("save")){
                File file = new File(Prison.getInstance().getDataFolder() + "/items.yml");
                FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
                String id = args[1];
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.setUnbreakable(true);
                itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
                itemConfig.set(id,itemStack);
                try {
                    itemConfig.save(file);
                    ChatUtils.toolChat(player,"Item created.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(args[0].equalsIgnoreCase("list")){
                File file = new File(Prison.getInstance().getDataFolder() + "/items.yml");
                System.out.println("FILE: " + file.toString());
                FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
                ChatUtils.toolChat(player,"Server Items: ");
                for(String item : itemConfig.getKeys(false)){
                    ChatUtils.basicChat(player,item);
                }
            } else if(args[0].equalsIgnoreCase("give")){
                File file = new File(Prison.getInstance().getDataFolder() + "/items.yml");
                FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
                if(args.length < 2){
                    ChatUtils.chatError(player,"Please include an argument: /it give <item>");
                    return true;
                }
                if(!itemConfig.isSet(args[1])){
                    ChatUtils.chatError(player,"Please specify a real item. View with ./it list");
                    return true;
                }
                ItemStack itemStack = itemConfig.getItemStack(args[1]);
                if(args.length >= 3){
                    int amount = Integer.parseInt(args[2]);
                    if(amount >= 1 && amount <= 64){
                        itemStack.setAmount(amount);
                    } else {
                        ChatUtils.chatError(player,"Please specify a number between 0-64");
                        return true;
                    }
                }
                player.getInventory().addItem(itemStack);
                ChatUtils.toolChat(player,"Item received.");
            } else if(args[0].equalsIgnoreCase("delete")){
                File file = new File(Prison.getInstance().getDataFolder() + "/items.yml");
                FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
                if(args.length < 2){
                    ChatUtils.chatError(player,"Please include an argument: /it delete <item>");
                    return true;
                }
                itemConfig.set(args[1],null);
                ChatUtils.toolChat(player,"Item deleted.");
                try {
                    itemConfig.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(args[0].equalsIgnoreCase("tag")){
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                NBTItem nbtItem = new NBTItem(itemStack);
                if(args.length <= 2){
                    nbtItem.addCompound(args[1]);
                    ChatUtils.toolChat(player,"Tag added to item.");
                } else{
                    nbtItem.setString(args[1],args[2]);
                    ChatUtils.toolChat(player,"Compound added to item.");
                }
                player.getInventory().setItemInMainHand(nbtItem.getItem());
            } else if(args[0].equalsIgnoreCase("tags")){
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                NBTItem nbtItem = new NBTItem(itemStack);
                ChatUtils.toolChat(player,"Tags:");
                for(String key : nbtItem.getKeys()){
                    ChatUtils.basicChat(player,key + " : " + nbtItem.getString(key));
                }
            } else if(args[0].equalsIgnoreCase("rtag")){
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                NBTItem nbtItem = new NBTItem(itemStack);
                if(args.length < 2){
                    ChatUtils.chatError(player,"Please include an argument: /it rtag <tag>");
                }
                nbtItem.removeKey(args[1]);
                ChatUtils.toolChat(player,"Tag has been removed,");
            } else {
                ChatUtils.chatCommand(player,"list, give, save, delete, tag, tags, rtag");
            }
        }
        return true;
    }
}
