package com.qa_zombie.void_main;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;

import java.util.Comparator;

import com.qa_zombie.configuration.ZombieSettingsConfiguration;
import com.qa_zombie.QaZombieMod;

@EventBusSubscriber
public class EntityTick {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity detectedPlayer = null;
		BlockState offhandedBlock = Blocks.AIR.defaultBlockState();
		BlockState detectedBlock = Blocks.AIR.defaultBlockState();
		boolean canBreak = false;
		double distance = 0;
		double correctTool = 0;
		if (entity.getPersistentData().getDouble("tntPlacingTick") > 0) {
			entity.getPersistentData().putDouble("tntPlacingTick", (entity.getPersistentData().getDouble("tntPlacingTick") - 1));
		}
		if (entity.getPersistentData().getDouble("blockPlacingTick") > 0) {
			entity.getPersistentData().putDouble("blockPlacingTick", (entity.getPersistentData().getDouble("blockPlacingTick") - 1));
		}
		if (entity.getPersistentData().getDouble("playerSearchTick") <= 400) {
			entity.getPersistentData().putDouble("playerSearchTick", (entity.getPersistentData().getDouble("playerSearchTick") + 1));
		}
		if (entity.getPersistentData().getDouble("breakProcess") >= 2) {
			if (entity instanceof LivingEntity _entity)
				_entity.swing(InteractionHand.MAIN_HAND, true);
			entity.getPersistentData().putDouble("breakProcess", (entity.getPersistentData().getDouble("breakProcess") - 1));
		} else if (entity.getPersistentData().getDouble("breakProcess") == 1) {
			world.destroyBlock(BlockPos.containing(Math.signum((entity.getLookAngle()).x()) + x, Math.signum((entity.getLookAngle()).y()) + y + 1, Math.signum((entity.getLookAngle()).z()) + z), false);
			world.destroyBlock(BlockPos.containing(x, y + 1, z), false);
			entity.getPersistentData().putDouble("breakProcess", 0);
			entity.getPersistentData().putDouble("blockBreakTick", Math.round((double) ZombieSettingsConfiguration.ZOMBIE_BLOCK_BREAK_COOLDOWN.get()));
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("playsound " + BuiltInRegistries.BLOCK
								.getKey((world.getBlockState(BlockPos.containing(Math.signum((entity.getLookAngle()).x()) + x, Math.signum((entity.getLookAngle()).y()) + y + 1, Math.signum((entity.getLookAngle()).z()) + z))).getBlock()).toString()
								+ ".break block @a ~ ~ ~ 1.0 1.0"));
		} else {
			if (entity.getPersistentData().getDouble("blockBreakTick") > 0) {
				entity.getPersistentData().putDouble("blockBreakTick", (entity.getPersistentData().getDouble("blockBreakTick") - 1));
			}
		}
		if (entity.getPersistentData().getDouble("playerSearchTick") > 400) {
			if (!(world.getNearestPlayer(x, y, z, 40, true) == null) && getEntityGameType((findEntityInWorldRange(world, Player.class, x, y, z, 40))) == GameType.SURVIVAL) {
				detectedPlayer = findEntityInWorldRange(world, Player.class, x, y, z, 40);
				{
					final Vec3 _center = new Vec3(x, y, z);
					for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
						if ((entityiterator instanceof Zombie) && !(entityiterator instanceof ZombifiedPiglin)) {
							if (entityiterator instanceof Mob _entity && detectedPlayer instanceof LivingEntity _ent)
								_entity.setTarget(_ent);
							entityiterator.getPersistentData().putDouble("playerSearchTick", (-1600));
						}
					}
				}
			} else {
				entity.getPersistentData().putDouble("playerSearchTick", 0);
			}
		}
		if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof Player && (entity instanceof Zombie) && !(entity instanceof ZombifiedPiglin)) {
			distance = ((entity.position()).subtract(((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).position()))).length();
			if (distance <= 5 && (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.TNT.asItem() && entity.getPersistentData().getDouble("tntPlacingTick") <= 0) {
				entity.getPersistentData().putDouble("tntPlacingTick", ((double) ZombieSettingsConfiguration.ZOMBIE_TNT_PLACE_COOLDOWN_TICKS.get()));
				if (ZombieSettingsConfiguration.ZOMBIE_TNT_INSTANT_IGNITION_ENABLED.get()) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"summon minecraft:tnt ~ ~ ~");
				} else {
					world.setBlock(BlockPos.containing(x, y, z), Blocks.TNT.defaultBlockState(), 3);
					QaZombieMod.queueServerWork(30, () -> {
						if (Blocks.TNT == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
							world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
							if (world instanceof ServerLevel _level)
								_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
										"summon minecraft:tnt ~ ~ ~ {fuse:20}");
						}
					});
				}
				if (entity.getPersistentData().getDouble("blockTNTLast") >= 2) {
					entity.getPersistentData().putDouble("blockTNTLast", (entity.getPersistentData().getDouble("blockInventoryLast") - 1));
				} else if (entity.getPersistentData().getDouble("blockTNTLast") == 1) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack72 = new ItemStack(Blocks.AIR).copy();
						_setstack72.setCount(1);
						_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack72);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				}
				entity.getPersistentData().putDouble("blockTNTLast", 0);
			}
			offhandedBlock = ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() instanceof BlockItem _bi ? _bi.getBlock().defaultBlockState() : Blocks.AIR.defaultBlockState());
			if (distance <= 20 && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY() - entity.getY() >= 2 && entity.getPersistentData().getDouble("blockPlacingTick") <= 0 && !(offhandedBlock.getBlock() == Blocks.AIR)
					&& !(offhandedBlock.getBlock() == Blocks.TNT)) {
				world.setBlock(BlockPos.containing(x, y, z), offhandedBlock, 3);
				entity.getPersistentData().putDouble("blockPlacingTick", ((double) ZombieSettingsConfiguration.ZOMBIE_BLOCK_PLACE_COOLDOWN_TICKS.get()));
				if (entity.getPersistentData().getDouble("blockInventoryLast") >= 2) {
					entity.getPersistentData().putDouble("blockInventoryLast", (entity.getPersistentData().getDouble("blockInventoryLast") - 1));
				} else if (entity.getPersistentData().getDouble("blockInventoryLast") == 1) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack89 = new ItemStack(Blocks.AIR).copy();
						_setstack89.setCount(1);
						_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack89);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				}
				entity.getPersistentData().putDouble("blockInventoryLast", 0);
			}
			if (entity.getPersistentData().getDouble("breakProcess") <= 0 && entity.getPersistentData().getDouble("blockBreakTick") <= 0 && distance <= 10
					&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.IRON_PICKAXE) {
				detectedBlock = (world.getBlockState(BlockPos.containing(Math.signum((entity.getLookAngle()).x()) + x, Math.signum((entity.getLookAngle()).y()) + y + 1, Math.signum((entity.getLookAngle()).z()) + z)));
				canBreak = true;
				for (String stringiterator : ZombieSettingsConfiguration.BREAKABLE_BLOCK_BLACKLIST.get()) {
					if ((stringiterator).equals(BuiltInRegistries.BLOCK.getKey(detectedBlock.getBlock()).toString())) {
						canBreak = false;
						break;
					}
				}
				if (canBreak) {
					for (String stringiterator : ZombieSettingsConfiguration.BREAKABLE_BLOCK_TAGS.get()) {
						if (detectedBlock.is(BlockTags.create(ResourceLocation.parse((stringiterator).toLowerCase(java.util.Locale.ENGLISH))))) {
							canBreak = false;
							break;
						}
					}
					if (canBreak) {
						for (String stringiterator : ZombieSettingsConfiguration.BREAKABLE_BLOCK_LIST.get()) {
							if ((stringiterator).equals(BuiltInRegistries.BLOCK.getKey(detectedBlock.getBlock()).toString())) {
								canBreak = false;
								break;
							}
						}
					}
				}
				if (!canBreak) {
					correctTool = 100;
					if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).isCorrectToolForDrops(detectedBlock)) {
						correctTool = 30;
					}
					entity.getPersistentData().putDouble("breakProcess", Math.max(20,
							Math.ceil((detectedBlock.getDestroySpeed(world, BlockPos.containing(0, 0, 0)) * correctTool) / ((double) ZombieSettingsConfiguration.ZOMBIE_DIGGING_BASE_EFFICIENCY.get() * (Math
									.pow((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY)), 2)
									+ 1)))));
				}
			}
		}
	}

	private static Entity findEntityInWorldRange(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		return (Entity) world.getEntitiesOfClass(clazz, AABB.ofSize(new Vec3(x, y, z), range, range, range), e -> true).stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(x, y, z))).findFirst().orElse(null);
	}

	private static GameType getEntityGameType(Entity entity) {
		if (entity instanceof ServerPlayer serverPlayer) {
			return serverPlayer.gameMode.getGameModeForPlayer();
		} else if (entity instanceof Player player && player.level().isClientSide()) {
			PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
			if (playerInfo != null)
				return playerInfo.getGameMode();
		}
		return null;
	}
}