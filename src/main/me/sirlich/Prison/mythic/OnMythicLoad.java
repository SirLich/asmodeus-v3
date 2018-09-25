package main.me.sirlich.Prison.mythic;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicDropLoadEvent;
import io.lumine.xikage.mythicmobs.drops.Drop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnMythicLoad implements Listener
{
    @EventHandler
    public void onMythicDropLoad(MythicDropLoadEvent event)	{
        if(event.getDropName().toUpperCase().equals("LICHPRISON"))	{
            Drop drop = new LichPrisonDrop(event.getContainer().getLine(), event.getConfig());
            event.register(drop);
        }
    }
}
