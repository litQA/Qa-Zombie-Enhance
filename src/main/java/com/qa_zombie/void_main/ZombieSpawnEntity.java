package com.qa_zombie.void_main;

import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import javax.annotation.Nullable;

import java.util.ArrayList;

import com.qa_zombie.network.QaZombieModVariables;
import com.qa_zombie.configuration.ZombieSettingsConfiguration;

@EventBusSubscriber
public class ZombieSpawnEntity {
	@SubscribeEvent
	public static void onEntitySpawned(EntityJoinLevelEvent event) {
		execute(event, event.getLevel(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double day = 0;
		double level = 0;
		double addedAttribute = 0;
		double attributeCountDay = 0;
		double startDay = 0;
		double addedBase = 0;
		double equipStartDay = 0;
		double chancePixaxe = 0;
		double chanceBlock = 0;
		double chanceTNT = 0;
		double sumOfChance = 0;
		double eventuallyItem = 0;
		double random01 = 0;
		ArrayList<Object> advancedEquip = new ArrayList<>();
		ArrayList<Object> normalEquip = new ArrayList<>();
		ArrayList<Object> possibleBlocks = new ArrayList<>();
		day = Math.max(1, Math.min((double) ZombieSettingsConfiguration.ZOMBIE_ATTRIBUTE_BONUS_STOP_DAY.get(), QaZombieModVariables.MapVariables.get(world).dayCount));
		if (ZombieSettingsConfiguration.ZOMBIE_ATTRIBUTE_BONUS_GLOBAL.get() && (entity instanceof Zombie) && !(entity instanceof ZombifiedPiglin)) {
			entity.getPersistentData().putDouble("playerSearchTick", 0);
			if (entity instanceof LivingEntity _livEnt4 && _livEnt4.isBaby() && ZombieSettingsConfiguration.DISABLE_BABY_ZOMBIE_SPAWNING.get()) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			}
			startDay = (double) ZombieSettingsConfiguration.ZOMBIE_ATTRIBUTE_BONUS_START_DAY.get();
			if (day >= startDay) {
				attributeCountDay = Math.max(startDay, 1) - day + 1;
				addedBase = (double) ZombieSettingsConfiguration.ZOMBIE_HEALTH_BONUS_MULTIPLIER.get();
				if (addedBase > 0) {
					addedAttribute = AttributeCalculator.execute(addedBase, attributeCountDay, ZombieSettingsConfiguration.ZOMBIE_HEALTH_BONUS_TYPE.get(), ZombieSettingsConfiguration.ZOMBIE_HEALTH_BONUS_DISTRIBUTION_MODE.get());
					if (entity instanceof LivingEntity _entity) {
						AttributeModifier modifier = new AttributeModifier(ResourceLocation.parse("qa_zombie:day_health"), Math.max(addedAttribute, 0), AttributeModifier.Operation.ADD_VALUE);
						if (!_entity.getAttribute(Attributes.MAX_HEALTH).hasModifier(modifier.id())) {
							_entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(modifier);
						}
					}
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) (entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity11.getAttribute(Attributes.MAX_HEALTH).getValue() : 0));
				}
				addedBase = (double) ZombieSettingsConfiguration.ZOMBIE_ARMOR_BONUS_MULTIPLIER.get();
				if (addedBase > 0) {
					addedAttribute = AttributeCalculator.execute(addedBase, attributeCountDay, ZombieSettingsConfiguration.ZOMBIE_ARMOR_BONUS_TYPE.get(), ZombieSettingsConfiguration.ZOMBIE_ARMOR_BONUS_DISTRIBUTION_MODE.get());
					if (entity instanceof LivingEntity _entity) {
						AttributeModifier modifier = new AttributeModifier(ResourceLocation.parse("qa_zombie:day_armor"), addedAttribute, AttributeModifier.Operation.ADD_VALUE);
						if (!_entity.getAttribute(Attributes.ARMOR).hasModifier(modifier.id())) {
							_entity.getAttribute(Attributes.ARMOR).addPermanentModifier(modifier);
						}
					}
				}
				addedBase = (double) ZombieSettingsConfiguration.ZOMBIE_ATTACK_DAMAGE_BONUS_MULTIPLIER.get();
				if (addedBase > 0) {
					addedAttribute = AttributeCalculator.execute(addedBase, attributeCountDay, ZombieSettingsConfiguration.ZOMBIE_ATTACK_DAMAGE_BONUS_TYPE.get(),
							ZombieSettingsConfiguration.ZOMBIE_ATTACK_DAMAGE_BONUS_DISTRIBUTION_MODE.get());
					if (entity instanceof LivingEntity _entity) {
						AttributeModifier modifier = new AttributeModifier(ResourceLocation.parse("qa_zombie:day_attack"), addedAttribute, AttributeModifier.Operation.ADD_VALUE);
						if (!_entity.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(modifier.id())) {
							_entity.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(modifier);
						}
					}
				}
			}
			equipStartDay = (double) ZombieSettingsConfiguration.ZOMBIE_EQUIP_UNLOCK_DAY.get();
			if (day >= equipStartDay) {
				level = Math.round((day - equipStartDay) / 10);
				normalEquip.add(toSupportedType("iron"));
				normalEquip.add(toSupportedType("golden"));
				normalEquip.add(toSupportedType("chainmail"));
				normalEquip.add(toSupportedType("leather"));
				advancedEquip.add(toSupportedType("diamond"));
				advancedEquip.add(toSupportedType("netherite"));
				for (int index0 = 0; index0 < (int) level; index0++) {
					normalEquip.addAll(advancedEquip);
				}
				for (int index1 = 0; index1 < (int) Math.max(0, 6 - level); index1++) {
					normalEquip.add(toSupportedType("null"));
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.getInventory().armor.set(3,
								(EnchantmentHelper.enchantItem(world.getRandom(),
										new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(
												(("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_helmet")).toLowerCase(java.util.Locale.ENGLISH)))),
										(int) (5 * level),
										(true)
												? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
												: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream())));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.HEAD,
								(EnchantmentHelper.enchantItem(world.getRandom(),
										new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(
												(("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_helmet")).toLowerCase(java.util.Locale.ENGLISH)))),
										(int) (5 * level),
										(true)
												? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
												: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream())));
					}
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.getInventory().armor.set(2,
								(EnchantmentHelper.enchantItem(world.getRandom(),
										new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(
												(("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_chestplate")).toLowerCase(java.util.Locale.ENGLISH)))),
										(int) (5 * level),
										(true)
												? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
												: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream())));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.CHEST,
								(EnchantmentHelper.enchantItem(world.getRandom(),
										new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(
												(("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_chestplate")).toLowerCase(java.util.Locale.ENGLISH)))),
										(int) (5 * level),
										(true)
												? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
												: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream())));
					}
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.getInventory().armor.set(1,
								(EnchantmentHelper.enchantItem(world.getRandom(),
										new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(
												(("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_leggings")).toLowerCase(java.util.Locale.ENGLISH)))),
										(int) (5 * level),
										(true)
												? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
												: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream())));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.LEGS,
								(EnchantmentHelper.enchantItem(world.getRandom(),
										new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(
												(("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_leggings")).toLowerCase(java.util.Locale.ENGLISH)))),
										(int) (5 * level),
										(true)
												? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
												: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream())));
					}
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.getInventory().armor.set(0,
								(EnchantmentHelper.enchantItem(world.getRandom(),
										new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation
												.parse((("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_boots")).toLowerCase(java.util.Locale.ENGLISH)))),
										(int) (5 * level),
										(true)
												? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
												: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream())));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.FEET,
								(EnchantmentHelper.enchantItem(world.getRandom(),
										new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation
												.parse((("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_boots")).toLowerCase(java.util.Locale.ENGLISH)))),
										(int) (5 * level),
										(true)
												? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
												: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream())));
					}
				}
				if (Math.random() < AttributeCalculator.execute((double) ZombieSettingsConfiguration.ZOMBIE_WEAPON_BASE_CHANCE.get(), day - equipStartDay, ZombieSettingsConfiguration.ZOMBIE_WEAPON_CHANCE_GROWTH_TYPE.get(), "all")) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack61 = (EnchantmentHelper.enchantItem(world.getRandom(),
								new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation
										.parse((("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_sword")).toLowerCase(java.util.Locale.ENGLISH)))),
								(int) (5 * level),
								(true)
										? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
										: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream()))
								.copy();
						_setstack61.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack61);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				} else {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack67 = (EnchantmentHelper.enchantItem(world.getRandom(),
								new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation
										.parse((("minecraft:" + /*@String*/(getListElement(normalEquip, Mth.nextInt(RandomSource.create(), 0, (int) normalEquip.size()), String.class, "")) + "_axe")).toLowerCase(java.util.Locale.ENGLISH)))),
								(int) (5 * level),
								(true)
										? world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(reference -> (Holder<Enchantment>) reference)
										: world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE).get().stream()))
								.copy();
						_setstack67.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack67);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				}
			}
			if ((double) ZombieSettingsConfiguration.FIRE_RESISTANT_ZOMBIE_SPAWN_START_DAY.get() <= day && (double) ZombieSettingsConfiguration.FIRE_RESISTANT_ZOMBIE_SPAWN_START_DAY.get() >= 1
					&& Math.random() < ProbabilityCalculator.execute((double) ZombieSettingsConfiguration.FIRE_RESISTANT_ZOMBIE_BASE_SPAWN_CHANCE.get(), day - (double) ZombieSettingsConfiguration.FIRE_RESISTANT_ZOMBIE_SPAWN_START_DAY.get(),
							ZombieSettingsConfiguration.FIRE_RESISTANT_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE.get())) {
				if (ZombieSettingsConfiguration.FIRE_RESISTANCE_VISIBILITY.get()) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @s minecraft:fire_resistance infinite 0");
						}
					}
				} else {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "effect give @s minecraft:fire_resistance infinite 0 true");
						}
					}
				}
			}
			if ((double) ZombieSettingsConfiguration.PICKAXE_ZOMBIE_SPAWN_START_DAY.get() <= day && (double) ZombieSettingsConfiguration.PICKAXE_ZOMBIE_SPAWN_START_DAY.get() >= 1) {
				chancePixaxe = ProbabilityCalculator.execute((double) ZombieSettingsConfiguration.PICKAXE_ZOMBIE_BASE_SPAWN_CHANCE.get(), day - (double) ZombieSettingsConfiguration.PICKAXE_ZOMBIE_SPAWN_START_DAY.get() + 1,
						ZombieSettingsConfiguration.PICKAXE_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE.get());
			}
			if ((double) ZombieSettingsConfiguration.BLOCK_PLACING_ZOMBIE_SPAWN_START_DAY.get() <= day && (double) ZombieSettingsConfiguration.BLOCK_PLACING_ZOMBIE_SPAWN_START_DAY.get() >= 1) {
				chanceBlock = ProbabilityCalculator.execute((double) ZombieSettingsConfiguration.BLOCK_PLACING_ZOMBIE_BASE_SPAWN_CHANCE.get(), day - (double) ZombieSettingsConfiguration.BLOCK_PLACING_ZOMBIE_SPAWN_START_DAY.get() + 1,
						ZombieSettingsConfiguration.BLOCK_PLACING_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE.get());
			}
			if ((double) ZombieSettingsConfiguration.TNT_ZOMBIE_SPAWN_START_DAY.get() <= day && (double) ZombieSettingsConfiguration.TNT_ZOMBIE_SPAWN_START_DAY.get() >= 1) {
				chanceTNT = ProbabilityCalculator.execute((double) ZombieSettingsConfiguration.TNT_ZOMBIE_BASE_SPAWN_CHANCE.get(), day - (double) ZombieSettingsConfiguration.TNT_ZOMBIE_SPAWN_START_DAY.get() + 1,
						ZombieSettingsConfiguration.TNT_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE.get());
			}
			sumOfChance = chanceTNT + chancePixaxe + chanceBlock;
			random01 = Math.random();
			if (sumOfChance > 0 && sumOfChance < 1) {
				if (random01 <= chancePixaxe) {
					eventuallyItem = 1;
				} else if (random01 <= chancePixaxe + chanceBlock) {
					eventuallyItem = 2;
				} else if (random01 <= sumOfChance) {
					eventuallyItem = 3;
				} else {
					eventuallyItem = 0;
				}
			} else {
				random01 = random01 * sumOfChance;
				if (random01 <= chancePixaxe) {
					eventuallyItem = 1;
				} else if (random01 <= chancePixaxe + chanceBlock) {
					eventuallyItem = 2;
				} else {
					eventuallyItem = 3;
				}
			}
			if (1 == eventuallyItem) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack91 = new ItemStack(Items.IRON_PICKAXE).copy();
					_setstack91.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack91);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
			} else if (2 == eventuallyItem) {
				for (String stringiterator : ZombieSettingsConfiguration.ZOMBIE_POSSIBLE_BLOCKS.get()) {
					possibleBlocks.add(toSupportedType(stringiterator));
				}
				if (possibleBlocks.size() >= 1) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack100 = new ItemStack(BuiltInRegistries.ITEM
								.get(ResourceLocation.parse((/*@String*/(getListElement(possibleBlocks, Mth.nextInt(RandomSource.create(), 0, (int) (possibleBlocks.size() - 1)), String.class, ""))).toLowerCase(java.util.Locale.ENGLISH)))).copy();
						_setstack100.setCount(1);
						_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack100);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				}
				if ((double) ZombieSettingsConfiguration.ZOMBIE_PLACE_MAX_COUNT.get() >= 0) {
					entity.getPersistentData().putDouble("blockInventoryLast", (Mth.nextInt(RandomSource.create(), 16, (int) Math.max((double) ZombieSettingsConfiguration.ZOMBIE_PLACE_MAX_COUNT.get(), 16))));
				} else {
					entity.getPersistentData().putDouble("blockInventoryLast", (-1));
				}
			} else if (3 == eventuallyItem) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack106 = new ItemStack(Blocks.TNT).copy();
					_setstack106.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack106);
					ItemStack _setstack107 = new ItemStack(Items.FLINT_AND_STEEL).copy();
					_setstack107.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack107);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if ((double) ZombieSettingsConfiguration.ZOMBIE_TNT_MAX_COUNT.get() >= 1) {
					entity.getPersistentData().putDouble("blockTNTLast", Math.round((double) ZombieSettingsConfiguration.ZOMBIE_PLACE_MAX_COUNT.get()));
				} else {
					entity.getPersistentData().putDouble("blockTNTLast", (-1));
				}
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).enchant(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BLAST_PROTECTION), 4);
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).enchant(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BLAST_PROTECTION), 4);
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).enchant(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BLAST_PROTECTION), 4);
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).enchant(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BLAST_PROTECTION), 4);
			}
			if (0 == world.dimensionType().moonPhase(world.dayTime()) && Math.random() < 0.05) {
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "item replace entity @s armor.head with minecraft:player_head[profile={name:\"LitQA\"}]");
					}
				}
			}
		}
	}

	private static double toSupportedType(Number value) {
		return value.doubleValue();
	}

	private static <E> E toSupportedType(E e) {
		return e;
	}

	private static <E> E getListElement(ArrayList<Object> objects, int index, Class<E> eClass, Object defaultValue) {
		if (index < objects.size()) {
			var element = objects.get(index);
			if (eClass.isInstance(element)) {
				return eClass.cast(element);
			}
		}
		return eClass.cast(defaultValue);
	}
}