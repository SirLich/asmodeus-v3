package main.me.sirlich.Prison.core;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.items.ItemHandler;
import main.me.sirlich.Prison.items.RpgItemType;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;


public class RpgPlayer
{
    public RpgPlayer(Player player){
        this.player = player;
    }
    public PlayerState getPlayerState()
    {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState)
    {
        this.playerState = playerState;
    }


    private PlayerState playerState;

    public ItemStack[] getTempInventory()
    {
        return tempInventory;
    }

    public void setTempInventory(ItemStack[] tempInventory)
    {
        this.tempInventory = tempInventory;
    }

    private ItemStack[] tempInventory;

    public ItemStack[] getTempArmour()
    {
        return tempArmour;
    }

    public void setTempArmour(ItemStack[] tempArmour)
    {
        this.tempArmour = tempArmour;
    }

    private ItemStack[] tempArmour;


    public void safeReturnInventory(){
        Player player = this.getPlayer();

        ItemStack[] oldContents = player.getInventory().getContents();
        ItemStack[] oldArmourContents = player.getInventory().getArmorContents();


        ItemStack[] contents = RpgPlayerList.getRpgPlayer(player).getTempInventory();
        ItemStack[] armourContents = RpgPlayerList.getRpgPlayer(player).getTempArmour();

        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armourContents);

        for (ItemStack itemStack : oldContents){
            if(itemStack != null) {
                player.getInventory().addItem(itemStack);
            }
        }

        for (ItemStack itemStack : oldArmourContents){
            if(itemStack != null){
                player.getInventory().addItem(itemStack);
            }
        }
    }

    public void safeClearInventory(){
        Player player = this.getPlayer();
        for (ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack != null && !ItemHandler.getItemType(itemStack).equals(RpgItemType.QUEST_ITEM) && !ItemHandler.getItemType(itemStack).equals(RpgItemType.GATE_KEY)){
                player.getInventory().remove(itemStack);
            }
        }

        if(ItemHandler.shouldBeDropped(player.getInventory().getHelmet())){
            player.getInventory().setHelmet(null);
        } if(ItemHandler.shouldBeDropped(player.getInventory().getChestplate())){
            player.getInventory().setChestplate(null);
        } if(ItemHandler.shouldBeDropped(player.getInventory().getLeggings())){
            player.getInventory().setLeggings(null);
        } if(ItemHandler.shouldBeDropped(player.getInventory().getBoots())){
            player.getInventory().setBoots(null);
        }

    }

    public void clearAllEffect(){
        final Player player = this.getPlayer();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.getActivePotionEffects().clear();
    }
    public void savePlayerInventory()
    {
        Player player = this.getPlayer();
        RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        rpgPlayer.setTempInventory(contents);
        rpgPlayer.setTempArmour(armorContents);
    }

    public void clearPlayerFile(){
        File file = new File(Prison.getInstance().getDataFolder() + "/players/" + player.getUniqueId() + ".yml");
        file.delete();
        player.getInventory().clear();
        player.kickPlayer("Your data has been cleared. Please login again for a fresh start!");
    }


    public void safeDropInventory(){
        Player player = this.getPlayer();
        ItemStack[] playerContents = player.getInventory().getContents();
        ItemStack[] armourContents = player.getInventory().getArmorContents();
        World world = player.getWorld();
        for (ItemStack itemStack : playerContents){
            if(itemStack != null && ItemHandler.shouldBeDropped(itemStack)){
                ChatUtils.basicChat(player,"Droping: " + itemStack.getItemMeta().getDisplayName());
                world.dropItem(player.getLocation(),itemStack);
            }
        }

        for (ItemStack itemStack : armourContents){
            if(itemStack != null && ItemHandler.shouldBeDropped(itemStack)){
                player.getWorld().dropItem(player.getLocation(),itemStack);
            }
        }

    }
    public Player getPlayer()
    {
        return player;
    }

    private Player player;
}
