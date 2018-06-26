package main.me.sirlich.Prison.utils;


import main.me.sirlich.Prison.Prison;
import org.bukkit.World;

public class PrisonUtils
{
    public static boolean checkWorldValidity(World world){
        String eventWorld = world.getName();
        String serverWorld = Prison.getInstance().getWorld();
        return eventWorld.equals(serverWorld);
    }
}
