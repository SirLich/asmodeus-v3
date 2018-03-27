package main.me.sirlich.Prison.civilians;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftVillager;

public class Civilian extends EntityVillager
{
    private String name;
    private int profession;

    public Civilian(World world, String name, int profession)
    {
        super(world);
        this.name = name;
        this.profession = profession;
        this.bukkitEntity = new CraftCustomVillager(this.world.getServer(), this);
        this.addScoreboardTag("civilian");
    }

    @Override
    protected void r()
    {
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
    }

    @Override
    public GroupDataEntity a(DifficultyDamageScaler scaler, GroupDataEntity entity, boolean flag)
    {
        entity = super.a(scaler, entity, flag);
        this.setProfession(profession);
        this.setInvulnerable(true);
        return entity;
    }


    private static class CraftCustomVillager extends CraftVillager
    {

        private CraftCustomVillager(CraftServer server, EntityVillager parent)
        {
            super(server, parent);
        }

    }
}
