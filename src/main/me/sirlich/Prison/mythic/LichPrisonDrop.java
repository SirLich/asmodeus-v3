package main.me.sirlich.Prison.mythic;

import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitItemStack;
import io.lumine.xikage.mythicmobs.drops.Drop;
import io.lumine.xikage.mythicmobs.drops.DropMetadata;
import io.lumine.xikage.mythicmobs.drops.IMultiDrop;
import io.lumine.xikage.mythicmobs.drops.LootBag;
import io.lumine.xikage.mythicmobs.drops.droppables.ItemDrop;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import main.me.sirlich.Prison.items.ItemHandler;
import org.bukkit.inventory.ItemStack;

public class LichPrisonDrop extends Drop implements IMultiDrop
{

    private String itemType;

    public LichPrisonDrop(String line, MythicLineConfig config) {
        super(line, config);
        System.out.println(line);
        this.itemType = config.getString(new String[] {"type","t"}, dropVar);
        System.out.println(itemType);
    }

    @Override
    public LootBag get(DropMetadata meta) {
        final LootBag loot = new LootBag(meta);

        final ItemStack item = ItemHandler.getItem(itemType);

        loot.add(new ItemDrop(this.getLine(), (MythicLineConfig) this.getConfig(), new BukkitItemStack(item)));
        return loot;
    }
}
