package main.me.sirlich.Prison.civilians;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.handlers.GateHandler;
import main.me.sirlich.Prison.items.ItemHandler;
import main.me.sirlich.Prison.items.RpgItemType;
import main.me.sirlich.Prison.utils.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CivilianActions
{

    private static void increaseChatIndex(Player player, String mobID){
        String playerUuid = player.getUniqueId().toString();
        File playerYml = new File(Prison.getInstance().getDataFolder() + "/players/" + playerUuid + ".yml");

        if (playerYml.exists()) {
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerYml);

            Integer index = playerConfig.getInt("quests.civilians." + mobID +".index");
            ++index;
            playerConfig.set("quests.civilians." + mobID +".index", index);
            Integer index1 = playerConfig.getInt("quests.civilians." + mobID +".index");
            System.out.println(index1);
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

    private static boolean checkItems(Player player, ItemStack costStack)
    {
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
    private static boolean consumeItems(Player player, ItemStack costStack)
    {
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

    private static String getRandomQuote(List<String> quotes){
        Random rand = new Random();
        return quotes.get(rand.nextInt(quotes.size()));
    }
    public static void doAction(Player player, String mobID, int index){

        //FairBrew Holms
        if(mobID.equals("5edbbdea-2efa-11e8-b467-0ed5f89f718b")){
            if(index == 6){
                player.getInventory().addItem(ItemHandler.getItem(RpgItemType.STARTER_SWORD));
                increaseChatIndex(player,mobID);
            } else if(index == 7){
                if(consumeItems(player, ItemHandler.getItem(RpgItemType.RAT_MEAT, 5))){
                    consumeItems(player,ItemHandler.getItem(RpgItemType.STARTER_SWORD));
                    ChatUtils.civilianChat(player,"*Crunch!* that was delicious!");
                    increaseChatIndex(player,mobID);
                } else{
                    List<String> quotes = new ArrayList<String>();
                    quotes.add("Have you got me my rats?");
                    quotes.add("I'm hungry you bastard. Bring. me. MEAT!");
                    quotes.add("You can find rats down that way a bit...");
                    quotes.add("Rats are nasty creatures... I woulden't eat them if I didn't have to!");

                    ChatUtils.civilianChat(player,(getRandomQuote(quotes)));
                }
            } else if(index == 8){
                GateHandler.giveGatePermision(player,14);//Red gates!
                increaseChatIndex(player,mobID);
            } else if(index == 9){
                List<String> quotes = new ArrayList<String>();
                quotes.add("Screw off! I DON'T WANT YOU HERE");
                quotes.add("...");
                quotes.add("I have nothing to say to you");
                quotes.add("I've been here thirty years...");
                ChatUtils.civilianChat(player,(getRandomQuote(quotes)));
            }else{
                increaseChatIndex(player,mobID);
            }
        }

        //Erin Top
        if(mobID.equals("80215646-2fdf-11e8-b467-0ed5f89f718b")){
            if(index == 1){
                List<String> quotes = new ArrayList<String>();
                quotes.add("I woulden't go down there if I were you.");
                quotes.add("The prison was taken over by the inmates 10 years ago.");
                quotes.add("Stay back!");
                quotes.add("There ain't no getting out alive!");
                ChatUtils.civilianChat(player,(getRandomQuote(quotes)));
            } else{
                increaseChatIndex(player,mobID);
            }
        }
    }
}
