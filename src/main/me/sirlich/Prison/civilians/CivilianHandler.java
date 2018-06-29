package main.me.sirlich.Prison.civilians;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.items.ItemHandler;
import main.me.sirlich.Prison.items.RpgItemType;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.*;

public class CivilianHandler implements Listener
{

    @EventHandler
    public void onPlayerClickNPC(PlayerInteractEntityEvent event)
    {

        /*
        This event catches the actual game click.
         */
        if(event.getRightClicked() instanceof Villager){
            event.setCancelled(true);
            Entity entity = event.getRightClicked();
            Player player = event.getPlayer();
            String playerUUID = player.getUniqueId().toString();
            File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUUID + ".yml");

            if (playerYml.exists()) {
                FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);

                //If core config does not contain the NPC, add some default setters
                if(!playerConfig.contains("quests.civilians." + main.me.sirlich.Prison.civilians.CivilianUtils.getUniqueMobID(entity.getUniqueId()))){
                    playerConfig.set("quests.civilians." + main.me.sirlich.Prison.civilians.CivilianUtils.getUniqueMobID(entity.getUniqueId())+".index",0);
                    playerConfig.set("quests.civilians." + main.me.sirlich.Prison.civilians.CivilianUtils.getUniqueMobID(entity.getUniqueId())+".stage","start");
                    try {
                        playerConfig.save(playerYml);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Integer index = playerConfig.getInt("quests.civilians." + main.me.sirlich.Prison.civilians.CivilianUtils.getUniqueMobID(entity.getUniqueId())+".index");
                String stage = playerConfig.getString("quests.civilians." + main.me.sirlich.Prison.civilians.CivilianUtils.getUniqueMobID(entity.getUniqueId())+".stage");

                //Where the magic happens!
                handleInteraction(player,stage,index, main.me.sirlich.Prison.civilians.CivilianUtils.getUniqueMobID(entity.getUniqueId()));
            } else {
                player.kickPlayer("Please contact the server developers. Your core file may be corrupt!");
            }
        }
    }


    public static void increaseChatIndex(Player player, String mobID){
        String playerUuid = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUuid + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);

            Integer index = playerConfig.getInt("quests.civilians." + mobID +".index");
            ++index;
            playerConfig.set("quests.civilians." + mobID +".index", index);
            try {
                playerConfig.save(playerYml);
                //Prison.getInstance().saveResource(Prison.getInstance().getDataFolder() + "/players/" + playerUuid + ".yml",false);
                System.out.println("SAVED!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            player.kickPlayer("Please contact the server developers. Your core file may be corrupt!");
        }
    }
    public static void setChatIndex(Player player, String mobID, int index){
        String playerUuid = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUuid + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
            playerConfig.set("quests.civilians." + mobID +".index", index);
            try {
                playerConfig.save(playerYml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            player.kickPlayer("Please contact the server developers. Your core file may be corrupt!");
        }
    }

    public static void setPlayerStage(Player player, String mobID, String stage){
        String playerUuid = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUuid + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
            playerConfig.set("quests.civilians." + mobID +".stage", stage);
            try {
                playerConfig.save(playerYml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            player.kickPlayer("Please contact the server developers. Your core file may be corrupt!");
        }
    }


    private void handleAction(Player player, Map<?,?> actionMap, FileConfiguration config, String base){
        String actionType = (String) actionMap.get("action_type");

        //GIVE
        if(actionType.equals("GIVE")){
            int amount = (Integer) actionMap.get("item_amount");
            ItemStack itemStack = ItemHandler.getItem(actionMap.get("item_type").toString(),amount);
            player.getInventory().addItem(itemStack);
        }

        //TAKE
        if(actionType.equals("TAKE")){
            int amount = (Integer) actionMap.get("item_amount");
            String itemStack = actionMap.get("item_type").toString();
            ItemHandler.consumeItems(player,itemStack, amount);
        }

        //SOUND
        if(actionType.equals("SOUND")){
            Sound sound = Sound.valueOf((String) actionMap.get("sound"));
            player.playSound(player.getLocation(),sound,1,1);
        }

        //GIVE_DOOR_PERMISSION
        if(actionType.equals("GIVE_DOOR_PERMISSION")){
            //giveGatePermision(player,(Integer)actionMap.get("door_number"));
        }
    }


    private void handleInteraction(Player player, String currentStage, int index, String uniqueMobID){
        File civilianYML = new File(Prison.getInstance().getDataFolder() + "/civilians/" + uniqueMobID + ".yml");
        if (civilianYML.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(civilianYML);


            //DATA
            String base = ("stages." + currentStage);
            String stageType = config.getString(base + ".stage_type");
            String nickname = config.getString("information.nickname");

            //LONG CHAT
            if(stageType.equals("LONG_CHAT")){
                List<String> chats = config.getStringList(base + ".chats");
                ChatUtils.civilianChat(player, nickname,chats.get(index));
                increaseChatIndex(player,uniqueMobID);
                if(config.isSet(base + ".actions")){
                    List<Map<?,?>> actions = config.getMapList(base + ".actions");
                    for(Map<?,?> action : actions){
                        handleAction(player,action,config,base);
                    }
                }
                if(index >= chats.size()-1){
                    if(config.isSet(base +".goto")){
                        setPlayerStage(player,uniqueMobID,config.getString(base + ".goto"));
                        setChatIndex(player,uniqueMobID,0);
                    }
                }
            }

            //BASIC
            if(stageType.equals("BASIC")){
                List<String> chats = config.getStringList(base + ".chats");
                Random r = new Random();
                String chat = chats.get(r.nextInt(chats.size()));
                ChatUtils.civilianChat(player,nickname,chat);
                if(config.isSet(base + ".actions")){
                    List<Map<?,?>> actions = config.getMapList(base + ".actions");
                    for(Map<?,?> action : actions){
                        handleAction(player,action,config,base);
                    }
                }
                if(config.getString(base + ".goto") != null){
                    setPlayerStage(player,uniqueMobID,config.getString(base + ".goto"));
                }
            }

            //LOGIC
            if(stageType.equals("LOGIC")){
                System.out.println("THIS IS: logic");
                String logicType = config.getString(base + ".logic_type");
                if(logicType.equals("HAS_ITEMS")){
                    int amount = config.getInt(base + ".item_amount");
                    String itemStack = config.getString(base + ".item_type");
                    String gotoStage = "";
                    if(ItemHandler.hasItems(player, itemStack, amount)){
                        gotoStage = config.getString(base + ".true_goto");
                    } else {
                        gotoStage = config.getString(base + ".false_goto");
                    }
                    setPlayerStage(player,uniqueMobID,gotoStage);
                    handleInteraction(player,gotoStage,0,uniqueMobID);
                } else {
                    System.out.println("MASSIVE ISSUE IN MOBS");
                }
            }
        } else {
            System.out.println("This entity is not registered!");
        }
    }
}
