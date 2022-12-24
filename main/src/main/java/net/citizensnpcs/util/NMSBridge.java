package net.citizensnpcs.util;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wither;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;

import net.citizensnpcs.api.ai.NavigatorParameters;
import net.citizensnpcs.api.command.CommandManager;
import net.citizensnpcs.api.command.exception.CommandException;
import net.citizensnpcs.api.jnbt.CompoundTag;
import net.citizensnpcs.api.npc.BlockBreaker;
import net.citizensnpcs.api.npc.BlockBreaker.BlockBreakerConfiguration;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.util.BoundingBox;
import net.citizensnpcs.npc.ai.MCNavigationStrategy.MCNavigator;
import net.citizensnpcs.npc.ai.MCTargetStrategy.TargetNavigator;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import net.citizensnpcs.trait.versioned.CamelTrait.CamelPose;

public interface NMSBridge {
    default void activate(Entity entity) {
    };

    public boolean addEntityToWorld(Entity entity, SpawnReason custom);

    public void addOrRemoveFromPlayerList(Entity entity, boolean remove);

    public void attack(LivingEntity attacker, LivingEntity target);

    public void cancelMoveDestination(Entity entity);

    public GameProfile fillProfileProperties(GameProfile profile, boolean requireSecure) throws Throwable;

    public BlockBreaker getBlockBreaker(Entity entity, Block targetBlock, BlockBreakerConfiguration config);

    public default Object getBossBar(Entity entity) {
        throw new UnsupportedOperationException();
    }

    public BoundingBox getBoundingBox(Entity handle);

    public BoundingBox getCollisionBox(Block block);

    public Location getDestination(Entity entity);

    public GameProfileRepository getGameProfileRepository();

    public float getHeadYaw(Entity entity);

    public double getHeight(Entity entity);

    public float getHorizontalMovement(Entity entity);

    public CompoundTag getNBT(ItemStack item);

    public NPC getNPC(Entity entity);

    public List<Entity> getPassengers(Entity entity);

    public GameProfile getProfile(SkullMeta meta);

    public String getSound(String flag) throws CommandException;

    public float getSpeedFor(NPC npc);

    public float getStepHeight(Entity entity);

    public TargetNavigator getTargetNavigator(Entity handle, Entity target, NavigatorParameters parameters);

    public MCNavigator getTargetNavigator(Entity entity, Iterable<Vector> dest, NavigatorParameters params);

    public MCNavigator getTargetNavigator(Entity entity, Location dest, NavigatorParameters params);

    public Entity getVehicle(Entity entity);

    public float getVerticalMovement(Entity entity);

    public double getWidth(Entity entity);

    public float getYaw(Entity entity);

    public boolean isOnGround(Entity entity);

    public boolean isSolid(Block in);

    public boolean isValid(Entity entity);

    public void load(CommandManager commands);

    public void loadPlugins();

    public void look(Entity from, Entity to);

    public void look(Entity entity, float yaw, float pitch);

    public void look(Entity entity, Location to, boolean headOnly, boolean immediate);

    public void mount(Entity entity, Entity passenger);

    public InventoryView openAnvilInventory(Player player, Inventory anvil, String title);

    public void openHorseScreen(Tameable horse, Player equipper);

    public void playAnimation(PlayerAnimation animation, Player player, int radius);

    public void playerTick(Player entity);

    public void registerEntityClass(Class<?> clazz);

    public void remove(Entity entity);

    public void removeFromServerPlayerList(Player player);

    public void removeFromWorld(org.bukkit.entity.Entity entity);

    public void removeHookIfNecessary(NPCRegistry npcRegistry, FishHook entity);

    public void replaceTrackerEntry(Player player);

    public void sendPositionUpdate(Player excluding, Entity from, Location location);

    public void sendRotationNearby(Entity from, float bodyYaw, float headYaw, float pitch);

    public boolean sendTabListAdd(Player recipient, Player listPlayer);

    public void sendTabListRemove(Player recipient, Collection<? extends SkinnableEntity> skinnableNPCs);

    public void sendTabListRemove(Player recipient, Player listPlayer);

    public void sendTeamPacket(Player recipient, Team team, int mode);;

    public default void setAllayDancing(Entity entity, boolean dancing) {
        throw new UnsupportedOperationException();
    }

    public void setBodyYaw(Entity entity, float yaw);

    public default void setCamelPose(Entity entity, CamelPose pose) {
        throw new UnsupportedOperationException();
    }

    public void setCustomName(Entity entity, Object component, String string);

    public void setDestination(Entity entity, double x, double y, double z, float speed);

    public void setEndermanAngry(Enderman enderman, boolean angry);

    public void setHeadYaw(Entity entity, float yaw);

    public void setKnockbackResistance(LivingEntity entity, double d);;

    public default void setLyingDown(Entity cat, boolean lying) {
        throw new UnsupportedOperationException();
    }

    public void setNavigationTarget(Entity handle, Entity target, float speed);

    public void setNoGravity(Entity entity, boolean nogravity);;

    public default void setPandaSitting(Entity entity, boolean sitting) {
        throw new UnsupportedOperationException();
    };

    public default void setPeekShulker(Entity entity, int peek) {
        throw new UnsupportedOperationException();
    };

    public default void setPiglinDancing(Entity entity, boolean dancing) {
        throw new UnsupportedOperationException();
    }

    public void setPitch(Entity entity, float pitch);;

    public default void setPolarBearRearing(Entity entity, boolean rearing) {
        throw new UnsupportedOperationException();
    }

    public void setProfile(SkullMeta meta, GameProfile profile);

    public void setShouldJump(Entity entity);

    public void setSitting(Ocelot ocelot, boolean sitting);

    public void setSitting(Tameable tameable, boolean sitting);

    public void setSneaking(Entity entity, boolean sneaking);

    public void setStepHeight(Entity entity, float height);

    public void setTeamNameTagVisible(Team team, boolean visible);

    public void setVerticalMovement(Entity bukkitEntity, double d);

    public void setWitherCharged(Wither wither, boolean charged);

    public boolean shouldJump(Entity entity);

    public void shutdown();

    public void sleep(Player entity, boolean sleep);

    public boolean tick(Entity next);

    public void trySwim(Entity entity);

    public void trySwim(Entity entity, float power);

    public void updateInventoryTitle(Player player, InventoryView view, String newTitle);

    public void updateNavigationWorld(Entity entity, World world);

    public void updatePathfindingRange(NPC npc, float pathfindingRange);
}
