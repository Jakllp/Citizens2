package net.citizensnpcs.nms.v1_13_R2.entity.nonliving;

import org.bukkit.entity.Minecart;
import org.bukkit.util.Vector;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.nms.v1_13_R2.entity.MobEntityController;
import net.citizensnpcs.nms.v1_13_R2.util.NMSImpl;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.ai.NPCHolder;
import net.citizensnpcs.util.Util;
import net.minecraft.server.v1_13_R2.EntityMinecartHopper;
import net.minecraft.server.v1_13_R2.FluidType;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.Tag;
import net.minecraft.server.v1_13_R2.World;

public class MinecartHopperController extends MobEntityController {
    public MinecartHopperController() {
        super(EntityMinecartHopperNPC.class);
    }

    @Override
    public Minecart getBukkitEntity() {
        return (Minecart) super.getBukkitEntity();
    }

    public static class EntityMinecartHopperNPC extends EntityMinecartHopper implements NPCHolder {
        private final CitizensNPC npc;

        public EntityMinecartHopperNPC(World world) {
            this(world, null);
        }

        public EntityMinecartHopperNPC(World world, NPC npc) {
            super(world);
            this.npc = (CitizensNPC) npc;
        }

        @Override
        public boolean b(Tag<FluidType> tag) {
            double mx = motX;
            double my = motY;
            double mz = motZ;
            boolean res = super.b(tag);
            if (!npc.isPushableByFluids()) {
                motX = mx;
                motY = my;
                motZ = mz;
            }
            return res;
        }

        @Override
        public void collide(net.minecraft.server.v1_13_R2.Entity entity) {
            // this method is called by both the entities involved - cancelling
            // it will not stop the NPC from moving.
            super.collide(entity);
            if (npc != null) {
                Util.callCollisionEvent(npc, entity.getBukkitEntity());
            }
        }

        @Override
        public boolean d(NBTTagCompound save) {
            return npc == null ? super.d(save) : false;
        }

        @Override
        public void f(double x, double y, double z) {
            Vector vector = Util.callPushEvent(npc, x, y, z);
            if (vector != null) {
                super.f(vector.getX(), vector.getY(), vector.getZ());
            }
        }

        @Override
        public NPC getNPC() {
            return npc;
        }

        @Override
        public void tick() {
            super.tick();
            if (npc != null) {
                npc.update();
                NMSImpl.minecartItemLogic(this);
            }
        }
    }
}