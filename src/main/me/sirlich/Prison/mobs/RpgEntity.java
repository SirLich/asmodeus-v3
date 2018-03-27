package main.me.sirlich.Prison.mobs;

import main.me.sirlich.Prison.Prison;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;


public class RpgEntity
{
    public RpgEntity(DropTable dropTable, Spawner parent, Entity entity){
        this.dropTable = dropTable;
        this.parent = parent;
        this.entity = entity;
    }

    public void startTeleportingTicker(){
        new BukkitRunnable() {
            public void run() {
                doTeleportingTicker();
            }

        }.runTaskLater(Prison.getInstance(), parent.getTeleportTicker());
    }

    private void doTeleportingTicker(){
        Location entityLoc = getEntity().getLocation();
        Location center = parent.getLocation();

        if(entityLoc.distance(center) >= parent.getRadius()){
            getEntity().teleport(center);
        }
        new BukkitRunnable() {
            public void run() {
                doTeleportingTicker();
            }

        }.runTaskLater(Prison.getInstance(), parent.getTeleportTicker());
    }
    private DropTable dropTable;

    public DropTable getDropTable()
    {
        return dropTable;
    }

    public void setDropTable(DropTable dropTable)
    {
        this.dropTable = dropTable;
    }

    public Spawner getParent()
    {
        return parent;
    }

    public void setParent(Spawner parent)
    {
        this.parent = parent;
    }

    private Spawner parent;

    public Entity getEntity()
    {
        return entity;
    }

    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    private Entity entity;
}
