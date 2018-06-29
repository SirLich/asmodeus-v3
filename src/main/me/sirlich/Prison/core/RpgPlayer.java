package main.me.sirlich.Prison.core;

import main.me.sirlich.Prison.items.ItemHandler;
import main.me.sirlich.Prison.items.RpgItemType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


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

        for (ItemStack itemStack : player.getInventory().getArmorContents()){
            if(itemStack != null && !ItemHandler.getItemType(itemStack).equals(RpgItemType.QUEST_ITEM) && !ItemHandler.getItemType(itemStack).equals(RpgItemType.GATE_KEY)){
                player.getInventory().remove(itemStack);
            }
        }
    }

    public void clearAllEffect(){
        Player player = this.getPlayer();
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

    public void safeDropInventory(){
        Player player = this.getPlayer();
        for (ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack != null && !ItemHandler.getItemType(itemStack).equals(RpgItemType.QUEST_ITEM) && !ItemHandler.getItemType(itemStack).equals(RpgItemType.GATE_KEY)){
                player.getWorld().dropItem(player.getLocation(),itemStack);
            }
        }

        for (ItemStack itemStack : player.getInventory().getArmorContents()){
            if(itemStack != null && !ItemHandler.getItemType(itemStack).equals(RpgItemType.QUEST_ITEM) && !ItemHandler.getItemType(itemStack).equals(RpgItemType.GATE_KEY)){
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
