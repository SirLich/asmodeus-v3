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

public class ItemCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
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
                FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
                ChatUtils.toolChat(player,"Server Items: ");
                for(String item : itemConfig.getKeys(false)){
                    ChatUtils.basicChat(player,item);
                }
            } else if(args[0].equalsIgnoreCase("give")){
                File file = new File(Prison.getInstance().getDataFolder() + "/items.yml");
                FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
                ItemStack itemStack = itemConfig.getItemStack(args[1]);
                player.getInventory().addItem(itemStack);
                ChatUtils.toolChat(player,"Item received.");
            } else if(args[0].equalsIgnoreCase("delete")){
                File file = new File(Prison.getInstance().getDataFolder() + "/items.yml");
                FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
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
                nbtItem.setString(args[1],args[2]);
                ChatUtils.toolChat(player,"Tag added to item.");
                player.getInventory().setItemInMainHand(nbtItem.getItem());
            } else if(args[0].equalsIgnoreCase("c")){
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                NBTItem nbtItem = new NBTItem(itemStack);
                nbtItem.addCompound(args[1]);
                ChatUtils.toolChat(player,"Compound added to item.");
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
                nbtItem.removeKey(args[1]);
                ChatUtils.toolChat(player,"tag has been removed");
            }
        }
        return true;
    }
}
