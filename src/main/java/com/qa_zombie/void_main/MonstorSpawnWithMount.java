package com.qa_zombie.void_main;

import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import javax.annotation.Nullable;

import com.qa_zombie.network.QaZombieModVariables;
import com.qa_zombie.configuration.ZombieSettingsConfiguration;

@EventBusSubscriber
public class MonstorSpawnWithMount {
	@SubscribeEvent
	public static void onEntitySpawned(EntityJoinLevelEvent event) {
		execute(event, event.getLevel(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity mount = null;
		Entity subDriver = null;
		for (String stringiterator : ZombieSettingsConfiguration.MOB_SPAWN_BLACKLIST.get()) {
			if ((stringiterator).equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString())) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			}
		}
		if (Math.random() < Math.max(0.1, 0.5 - 10 / QaZombieModVariables.MapVariables.get(world).dayCount) && ZombieSettingsConfiguration.MONSTER_SPAWN_WITH_MOUNT_ENABLED.get() && !world.isClientSide()) {
			if (entity instanceof Drowned) {
				entity.startRiding((world instanceof ServerLevel _level6 ? EntityType.SQUID.spawn(_level6, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED) : null));
			} else if (entity instanceof Husk) {
				mount = world instanceof ServerLevel _level9 ? EntityType.CAMEL.spawn(_level9, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED) : null;
				subDriver = world instanceof ServerLevel _level10 ? EntityType.BOGGED.spawn(_level10, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED) : null;
				entity.startRiding(mount);
				subDriver.startRiding(mount);
				{
					Entity _ent = subDriver;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @s minecraft:fire_resistance infinite 0 true");
					}
				}
			} else if (entity instanceof Zombie) {
				if (entity instanceof LivingEntity _livEnt15 && _livEnt15.isBaby()) {
					entity.startRiding((world instanceof ServerLevel _level16 ? EntityType.CHICKEN.spawn(_level16, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED) : null));
				} else {
					entity.startRiding((world instanceof ServerLevel _level18 ? EntityType.ZOMBIE_HORSE.spawn(_level18, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED) : null));
				}
			} else if (entity instanceof WitherSkeleton) {
				mount = world instanceof ServerLevel _level21 ? EntityType.CAVE_SPIDER.spawn(_level21, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED) : null;
				entity.startRiding(mount);
				{
					Entity _ent = mount;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @s minecraft:fire_resistance infinite 0 true");
					}
				}
			} else if (entity instanceof Skeleton && !(entity instanceof Bogged)) {
				entity.startRiding((world instanceof ServerLevel _level26 ? EntityType.SPIDER.spawn(_level26, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED) : null));
			} else if (entity instanceof Creeper) {
				mount = world instanceof ServerLevel _level29 ? EntityType.BEE.spawn(_level29, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED) : null;
				entity.startRiding(mount);
				{
					Entity _ent = mount;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @s minecraft:fire_resistance infinite 0 true");
					}
				}
			} else if (entity instanceof Piglin) {
				entity.startRiding((world instanceof ServerLevel _level33 ? EntityType.HOGLIN.spawn(_level33, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED) : null));
			}
		}
	}
}