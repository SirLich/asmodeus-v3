package main.me.sirlich.Prison.civilians;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.handlers.GateHandler;
import main.me.sirlich.Prison.items.ItemHandler;
import main.me.sirlich.Prison.items.RpgItemType;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.*;

public class CivilianHandler implements Listener
{

    public static HashMap<UUID, String> uniqueMobIDList = new HashMap<UUID, String>();

    private static void addCivilianToList(UUID uuid, String uniqueMobID){
        uniqueMobIDList.put(uuid,uniqueMobID);
    }

    public static String getUniqueMobID(UUID uuid){
        return uniqueMobIDList.get(uuid);
    }

    private static boolean checkItems(Player player, ItemStack costStack){
        //make sure we have enough
        int cost = costStack.getAmount();
        boolean hasEnough=false;
        for (ItemStack invStack : player.getInventory().getContents())
        {
            if(invStack == null)
                continue;
            if (invStack.getTypeId() == costStack.getTypeId()) {

                int inv = invStack.getAmount();
                if (cost - inv >= 0) {
                    cost = cost - inv;
                } else {
                    hasEnough=true;
                    break;
                }
            }
        }
        return hasEnough;
    }
    private static boolean consumeItems(Player player, ItemStack costStack) {
        if (!checkItems(player,costStack)){
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

    public static void doAction(Player player, String currentAction, FileConfiguration config){

        String nickname = config.getString("information.nickname");
        String mobID = config.getString("information.uuid");
        String nextAction = config.getString("actions." + currentAction + ".goto");
        String customActionID = config.getString("actions." + currentAction + ".custom_action_id");

        //FairBrew Holms
        if(mobID.equals("5edbbdea-2efa-11e8-b467-0ed5f89f718b")){
            if(customActionID.equals("give_starter_sword")){
                player.getInventory().addItem(ItemHandler.getItem(RpgItemType.STARTER_SWORD));
            }

            if(customActionID.equals("check_for_rat_meat")){
                if(consumeItems(player, ItemHandler.getItem(RpgItemType.RAT_MEAT, 5))){
                    player.playSound(player.getLocation(),Sound.ENTITY_GENERIC_EAT,1,1);
                    consumeItems(player,ItemHandler.getItem(RpgItemType.STARTER_SWORD));
                    CivilianHandler.setPlayerAction(player,mobID,nextAction);
                    ChatUtils.civilianChat(player,nickname,config.getStringList("actions." + currentAction + ".if_chat"));
                } else{
                    ChatUtils.civilianChat(player,nickname,config.getStringList("actions." + currentAction + ".else_chat"));
                }
            }

            if(customActionID.equals("give_red_key")){
                player.playSound(player.getLocation(),Sound.BLOCK_NOTE_GUITAR,1,1);
                GateHandler.giveGatePermision(player,14);//Red gates!
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
            player.kickPlayer("Please contact the server developers. Your player file may be corrupt!");
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
            player.kickPlayer("Please contact the server developers. Your player file may be corrupt!");
        }
    }

    public static void setPlayerAction(Player player, String mobID,String action){
        String playerUuid = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUuid + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);
            playerConfig.set("quests.civilians." + mobID +".action", action);
            try {
                playerConfig.save(playerYml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            player.kickPlayer("Please contact the server developers. Your player file may be corrupt!");
        }
    }

    private void handleInteraction(Player player, String action, int index, String uniqueMobID){
        //player.playSound(player.getLocation(), Sound.BLOCK_WOOD_HIT,1,1);
        File civilianYML = new File(Prison.getInstance().getDataFolder() + "/civilians/" + uniqueMobID + ".yml");
        if (civilianYML.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(civilianYML);
            String actionType = config.getString("actions." + action + ".action_type");
            String nickname = config.getString("information.nickname");

            if(actionType.equals("LONG_CHAT")){
                List<String> chats = config.getStringList("actions." + action + ".chats");
                ChatUtils.civilianChat(player, nickname,chats.get(index));
                increaseChatIndex(player,uniqueMobID);
                if(config.getString("actions." +action+".custom_action_id") != null){
                    doAction(player,action,config);
                }
                if(index > chats.size()){
                    if(config.getString("actions."+action +".goto") != null){
                        setChatIndex(player,uniqueMobID,0);
                        setPlayerAction(player,uniqueMobID,config.getString("actions." + action + ".goto"));
                        actionType = config.getString("actions."+action+".action_type");
                    }
                }
            }

            if(actionType.equals("BASIC")){
                List<String> chats = config.getStringList("actions." + action + ".chats");
                Random r = new Random();
                String chat = chats.get(r.nextInt(chats.size()));
                ChatUtils.civilianChat(player,nickname,chat);
                if(config.getString("actions." +action+".custom_action_id") != null){
                    doAction(player,action,config);
                }
                if(config.getString("actions."+action +".goto") != null){
                    setPlayerAction(player,uniqueMobID,config.getString("actions." + action + ".goto"));
                }
            }

            if(actionType.equals("IF_ELSE")){
                System.out.println(action);
                if(config.getString("actions." +action+".custom_action_id") != null){
                    doAction(player,action,config);
                }
            }
        } else {
            System.out.println("UNREGISTERED CIVILIAN!");
        }
    }


    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event)
    {
        if(event.getRightClicked() instanceof Villager){
            event.setCancelled(true);
            Entity entity = event.getRightClicked();
            Player player = event.getPlayer();
            String playerUUID = player.getUniqueId().toString();
            File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUUID + ".yml");

            if (playerYml.exists()) {
                FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);

                //If player config does not contain the NPC, add some defailt setters
                if(!playerConfig.contains("quests.civilians." + getUniqueMobID(entity.getUniqueId()))){
                    playerConfig.set("quests.civilians." + getUniqueMobID(entity.getUniqueId())+".index",0);
                    playerConfig.set("quests.civilians." + getUniqueMobID(entity.getUniqueId())+".action","start");
                    try {
                        playerConfig.save(playerYml);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Integer index = playerConfig.getInt("quests.civilians." + getUniqueMobID(entity.getUniqueId())+".index");
                String action = playerConfig.getString("quests.civilians." + getUniqueMobID(entity.getUniqueId())+".action");

                //Where the magic happens!
                handleInteraction(player,action,index,uniqueMobIDList.get(entity.getUniqueId()));
            } else {
                player.kickPlayer("Please contact the server developers. Your player file may be corrupt!");
            }
        }
    }



    /*
     * This is a public init
     */
    public static void spawnCivilians(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Prison.getInstance().getDataFolder() + "/civilians/civilians.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                civilianLoader(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void civilianLoader(String s){
        World world = Bukkit.getWorld(Prison.getInstance().getWorld());
        File civilianYML = new File(Prison.getInstance().getDataFolder() + "/civilians/" + s + ".yml");
        if (civilianYML.exists()){
            FileConfiguration config = YamlConfiguration.loadConfiguration(civilianYML);
            List<String> locs = Arrays.asList(config.getString("information.location").split(","));
            Location location = new Location(world, Double.parseDouble(locs.get(0)), Double.parseDouble(locs.get(1)), Double.parseDouble(locs.get(2)));

            String name = config.getString("information.name");
            Integer profession = config.getInt("information.profession");
            String uniqueMobID = config.getString("information.uuid");

            Civilian civilian = new Civilian(((CraftWorld) world).getHandle(), name, profession);
            civilian.setCustomName(ChatColor.DARK_GRAY + "~" + ChatColor.GRAY + name + ChatColor.DARK_GRAY + "~");
            civilian.setInvulnerable(true);
            civilian.setCustomNameVisible(true);

            addCivilianToList(civilian.getBukkitEntity().getUniqueId(), uniqueMobID);
            civilian.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

            ((CraftWorld) world).addEntity(civilian, CreatureSpawnEvent.SpawnReason.CUSTOM);
            System.out.println("Civilian successfully added...");

            try {
                config.save(civilianYML);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("WARNING! SOMETHING IS TERRIBLE WRONG IN CIVILIAN LOADER");
        }
    }
}
