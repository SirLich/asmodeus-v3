package main.me.sirlich.Prison.gates;

import de.tr7zw.itemnbtapi.NBTItem;
import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.core.PlayerState;
import main.me.sirlich.Prison.core.RpgPlayer;
import main.me.sirlich.Prison.core.RpgPlayerList;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;

public class GateHandler implements Listener
{

    //Handles gate key clicks
    @EventHandler
    public void onEnderEyeClick(PlayerInteractEvent event){
        Player player = (Player) event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(event.getItem() != null){
                ItemStack itemStack = event.getItem();
                NBTItem nbtItem = new NBTItem(itemStack);
                if(nbtItem.hasKey("item_type") && nbtItem.getString("item_type").equals("GATE_KEY")) {
                    String gate = nbtItem.getString("gate");
                    giveGatePermision(player, gate);
                    event.getItem().setAmount(itemStack.getAmount() -1);
                    event.setCancelled(true);
                    Sound sound = Sound.BLOCK_END_PORTAL_SPAWN;
                    player.playSound(player.getLocation(), sound, 1, 1);
                    ChatUtils.chatInfo(player, "Gate Unlocked!");
                }
            }
        }
    }

    public static void giveGatePermision(Player player, String gate){
        String playerUUID = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUUID + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
            playerConfig.set("gates." + gate,true);
            try {
                playerConfig.save(playerYml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static GateType getGateType(Block block){

        //Stained Glass!
        if(block.getType() == Material.STAINED_GLASS){
            if(block.getData() == 0){
                return GateType.WHITE_GLASS;
            } else if(block.getData() == 1){
                return GateType.ORANGE_GLASS;
            } else if(block.getData() == 2){
                return GateType.MAGENTA_GLASS;
            } else if(block.getData() == 3){
                return GateType.LIGHT_BLUE_GLASS;
            } else if(block.getData() == 4){
                return GateType.YELLOW_GLASS;
            } else if(block.getData() == 5){
                return GateType.LIME_GLASS;
            } else if(block.getData() == 6){
                return GateType.PINK_GLASS;
            } else if(block.getData() == 7){
                return GateType.GRAY_GLASS;
            } else if(block.getData() == 8){
                return GateType.LIGHT_GRAY_GLASS;
            } else if(block.getData() == 9){
                return GateType.CYAN_GLASS;
            } else if(block.getData() == 10){
                return GateType.PURPLE_GLASS;
            } else if(block.getData() == 11){
                return GateType.BLUE_GLASS;
            } else if(block.getData() == 12){
                return GateType.BROWN_GLASS;
            } else if(block.getData() == 13){
                return GateType.GREEN_GLASS;
            } else if(block.getData() == 14){
                return GateType.RED_GLASS;
            } else if(block.getData() == 15){
                return GateType.BLACK_GLASS;
            }
        } else if(block.getType() == Material.CONCRETE){
            if(block.getData() == 0){
                return GateType.WHITE_CONCRETE;
            } else if(block.getData() == 1){
                return GateType.ORANGE_CONCRETE;
            } else if(block.getData() == 2){
                return GateType.MAGENTA_CONCRETE;
            } else if(block.getData() == 3){
                return GateType.LIGHT_BLUE_CONCRETE;
            } else if(block.getData() == 4){
                return GateType.YELLOW_CONCRETE;
            } else if(block.getData() == 5){
                return GateType.LIME_CONCRETE;
            } else if(block.getData() == 6){
                return GateType.PINK_CONCRETE;
            } else if(block.getData() == 7){
                return GateType.GRAY_CONCRETE;
            } else if(block.getData() == 8){
                return GateType.LIGHT_GRAY_CONCRETE;
            } else if(block.getData() == 9){
                return GateType.CYAN_CONCRETE;
            } else if(block.getData() == 10){
                return GateType.PURPLE_CONCRETE;
            } else if(block.getData() == 11){
                return GateType.BLUE_CONCRETE;
            } else if(block.getData() == 12){
                return GateType.BROWN_CONCRETE;
            } else if(block.getData() == 13){
                return GateType.GREEN_CONCRETE;
            } else if(block.getData() == 14){
                return GateType.RED_CONCRETE;
            } else if(block.getData() == 15){
                return GateType.BLACK_CONCRETE;
            }
        }

        //Failsafe!
        return GateType.WHITE_GLASS;
    }


    private static boolean hasGatePermision(Player player, GateType gate){
        System.out.println(gate);
        String playerUUID = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUUID + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
            return playerConfig.getBoolean("gates." + gate);
        }
        return false;
    }

    private static boolean blockIsGate(Block block){
        return (block.getType() == Material.STAINED_GLASS || block.getType() == Material.CONCRETE);
    }

    public static void runGateTick(){
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            Location east = new Location(player.getWorld(), 1,-1,0);
            Block east_block = player.getEyeLocation().add(east).getBlock();

            Location west = new Location(player.getWorld(), -1,-1,0);
            Block west_block = player.getEyeLocation().add(west).getBlock();

            Location north = new Location(player.getWorld(), 0,-1,1);
            Block north_block = player.getEyeLocation().add(north).getBlock();

            Location south = new Location(player.getWorld(), 0,-1,-1);
            Block south_block = player.getEyeLocation().add(south).getBlock();

            if(blockIsGate(east_block)){
                east = east.multiply(3);
                if(hasGatePermision(player, getGateType(east_block))){
                    playMedia(player);
                    east.setY(0);
                    player.teleport(player.getLocation().add(east));
                    removeTutorial(player,east_block);
                }
            }
            if(blockIsGate(west_block)){
                west = west.multiply(3);
                if(hasGatePermision(player, getGateType(west_block))){
                    playMedia(player);
                    west.setY(0);
                    player.teleport(player.getLocation().add(west));
                    removeTutorial(player,west_block);
                }
            }
            if(blockIsGate(north_block)){
                north = north.multiply(3);
                if(hasGatePermision(player, getGateType(north_block))){
                    playMedia(player);
                    north.setY(0);
                    player.teleport(player.getLocation().add(north));
                    removeTutorial(player,north_block);
                }
            }
            if(blockIsGate(south_block)){
                south = south.multiply(3);
                if(hasGatePermision(player, getGateType(south_block))){
                    playMedia(player);
                    south.setY(0);
                    player.teleport(player.getLocation().add(south));
                    removeTutorial(player,south_block);
                }
            }
        }
    }

    private static void removeTutorial(Player player, Block block){
        GateType gateType = getGateType(block);
        RpgPlayer rpgPlayer = RpgPlayerList.getRpgPlayer(player);
        if(gateType.equals(GateType.RED_GLASS) && rpgPlayer.getPlayerState().equals(PlayerState.TUTORIAL)){
            ChatUtils.tutorialChat(player,"This concludes the tutorial. Welcome to The Dread Pits!");
            rpgPlayer.setPlayerState(PlayerState.BASIC);
        }
    }
    private static void playMedia(Player player){
        player.playSound(player.getLocation(), Prison.getInstance().getGateSound(),1,1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,3,1000));
    }
}
