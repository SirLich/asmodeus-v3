package main.me.sirlich.Prison.mobs;


import main.me.sirlich.Prison.items.RpgItemType;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class EntityHandler
{
    private static HashMap<RpgEntityType, CloneableEntity> entityMap = new HashMap<RpgEntityType,CloneableEntity>();

    public static CloneableEntity getRpgEntity(RpgEntityType rpgEntityType){
        return entityMap.get(rpgEntityType);
    }

    private static void createEntity(RpgEntityType rpgEntityType, EntityType entityType, String name, DropTable dropTable){
        CloneableEntity cloneableEntity = new CloneableEntity(entityType,name,dropTable);
        entityMap.put(rpgEntityType,cloneableEntity);
    }

    public static void initRpgEntities(){

        DropTable dropTable = new DropTable();
        dropTable.addItem(RpgItemType.RAT_MEAT, 0.4);
        dropTable.addItem(RpgItemType.RAT_BONE,0.5);
        dropTable.addItem(RpgItemType.GOLD_RING,0.05);
        createEntity(RpgEntityType.SEWER_RAT, EntityType.SILVERFISH, "Sewer Rat",dropTable);
    }
}
