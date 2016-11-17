package net.citizensnpcs.nms.v1_11_R1.entity.nonliving;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftLingeringPotion;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.util.Vector;

import net.citizensnpcs.api.event.NPCPushEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.nms.v1_11_R1.entity.MobEntityController;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.ai.NPCHolder;
import net.citizensnpcs.util.Util;
import net.minecraft.server.v1_11_R1.EntityPotion;
import net.minecraft.server.v1_11_R1.Items;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.World;

public class ThrownPotionController extends MobEntityController {
    public ThrownPotionController() {
        super(EntityThrownPotionNPC.class);
    }

    @Override
    public ThrownPotion getBukkitEntity() {
        return (ThrownPotion) super.getBukkitEntity();
    }

    public static class EntityThrownPotionNPC extends EntityPotion implements NPCHolder {
        private final CitizensNPC npc;

        public EntityThrownPotionNPC(World world) {
            this(world, null);
        }

        public EntityThrownPotionNPC(World world, NPC npc) {
            super(world);
            this.npc = (CitizensNPC) npc;
        }

        @Override
        public void collide(net.minecraft.server.v1_11_R1.Entity entity) {
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
            if (npc == null) {
                super.f(x, y, z);
                return;
            }
            if (NPCPushEvent.getHandlerList().getRegisteredListeners().length == 0) {
                if (!npc.data().get(NPC.DEFAULT_PROTECTED_METADATA, true))
                    super.f(x, y, z);
                return;
            }
            Vector vector = new Vector(x, y, z);
            NPCPushEvent event = Util.callPushEvent(npc, vector);
            if (!event.isCancelled()) {
                vector = event.getCollisionVector();
                super.f(vector.getX(), vector.getY(), vector.getZ());
            }
            // when another entity collides, this method is called to push the
            // NPC so we prevent it from doing anything if the event is
            // cancelled.
        }

        @Override
        public CraftEntity getBukkitEntity() {
            if (bukkitEntity == null && npc != null) {
                if (getItem() != null && getItem().getItem().equals(Items.LINGERING_POTION)) {
                    bukkitEntity = new LingeringThrownPotionNPC(this);
                } else {
                    bukkitEntity = new SplashThrownPotionNPC(this);
                }
            }
            return super.getBukkitEntity();
        }

        @Override
        public NPC getNPC() {
            return npc;
        }

        @Override
        public void A_() {
            if (npc != null) {
                npc.update();
            } else {
                super.A_();
            }
        }
    }

    public static class LingeringThrownPotionNPC extends CraftLingeringPotion implements NPCHolder {
        private final CitizensNPC npc;

        public LingeringThrownPotionNPC(EntityThrownPotionNPC entity) {
            super((CraftServer) Bukkit.getServer(), entity);
            this.npc = entity.npc;
        }

        @Override
        public NPC getNPC() {
            return npc;
        }
    }

    public static class SplashThrownPotionNPC extends CraftLingeringPotion implements NPCHolder {
        private final CitizensNPC npc;

        public SplashThrownPotionNPC(EntityThrownPotionNPC entity) {
            super((CraftServer) Bukkit.getServer(), entity);
            this.npc = entity.npc;
        }

        @Override
        public NPC getNPC() {
            return npc;
        }
    }
}