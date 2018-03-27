package main.me.sirlich.Prison.handlers;

import main.me.sirlich.Prison.mobs.RpgEntity;
import main.me.sirlich.Prison.mobs.RpgEntityList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathHandler implements Listener
{
    @EventHandler
    public void entityDeathEvent(EntityDeathEvent event){
        //Clear Item Drops
        event.getDrops().clear();

        if(RpgEntityList.containsEntity(event.getEntity().getUniqueId())){
            //Get RpgEntity
            RpgEntity rpgEntity = RpgEntityList.getRpgEntity(event.getEntity().getUniqueId());

            //Do item drops
            rpgEntity.getDropTable().doItemDrops(event.getEntity().getLocation());

            //Update parent
            rpgEntity.getParent().decreaseNumberOfLivingMobs();
            System.out.println("Mob killed!");
        } else {
            System.out.println("WARNING: A mob without the proper classing was just killed! Unable to handle drop-table!");
        }
    }
}
