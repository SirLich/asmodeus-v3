package main.me.sirlich.Prison.mobs;

import main.me.sirlich.Prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;


public class CloneableEntity
{
    private EntityType entityType;
    private DropTable dropTable;
    private int maxHealth;
    private int movementSpeed;
    private int attackSpeed;
    private String name;

    public void spawnClone(Location location, Spawner parent){
        Entity entity = Bukkit.getWorld(Prison.getInstance().getWorld()).spawnEntity(location,entityType);
        //TODO: Make these work
        //((Attributable) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        //((Attributable) entity).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(movementSpeed);
        //((Attributable) entity).getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed);

        RpgEntity rpgEntity = new RpgEntity(dropTable,parent,entity);
        RpgEntityList.addEntity(entity.getUniqueId(),rpgEntity);
        entity.setCustomName(name);
        ((LivingEntity) entity).setRemoveWhenFarAway(false);
    }

    public CloneableEntity(EntityType entityType, String name, DropTable dropTable){
        this.entityType = entityType;
        this.dropTable = dropTable;
        this.name = name;
    }
}
