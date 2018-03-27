package main.me.sirlich.Prison.mobs;

import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.UUID;

public class RpgEntityList
{
    private static HashMap<UUID, RpgEntity> rpgEntityMap = new HashMap<UUID, RpgEntity>();

    public static void addEntity(UUID uuid, RpgEntity rpgEntity){
        rpgEntityMap.put(uuid, rpgEntity);
        rpgEntity.startTeleportingTicker();
    }

    public static boolean containsEntity(UUID uuid){
        return rpgEntityMap.containsKey(uuid);
    }

    public static RpgEntity getRpgEntity(UUID uuid){
        return rpgEntityMap.get(uuid);
    }

}
