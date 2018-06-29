package main.me.sirlich.Prison.mobs;

import main.me.sirlich.Prison.Prison;
import main.me.sirlich.Prison.items.ItemHandler;
import main.me.sirlich.Prison.items.RpgItemType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DropTable
{
    private List<ItemStack> items = new ArrayList<ItemStack>();
    private List<Double> droprates = new ArrayList<Double>();

    public void addItem(ItemStack itemStack, Double droprate){
        items.add(itemStack);
        droprates.add(droprate);
    }

    public void addItem(String item, Double droprate){
        items.add(ItemHandler.getItem(item));
        droprates.add(droprate);
    }

    public void doItemDrops(Location location){
        for(int i = 0; i < items.size(); i++){
            double dropChance = Math.random();
            if(dropChance < droprates.get(i)){
                ItemStack itemStack = items.get(i);
                Bukkit.getWorld(Prison.getInstance().getWorld()).dropItem(location,itemStack);
            }
        }
    }
}
