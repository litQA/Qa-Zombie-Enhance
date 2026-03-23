package com.qa_zombie.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ZombieSettingsConfiguration {
	public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
	public static final ModConfigSpec SPEC;

	public static final ModConfigSpec.ConfigValue<Boolean> ZOMBIE_ATTRIBUTE_BONUS_GLOBAL;
	public static final ModConfigSpec.ConfigValue<Boolean> TELEPORT_REQUEST_FUNCTION;
	public static final ModConfigSpec.ConfigValue<Boolean> DISABLE_BABY_ZOMBIE_SPAWNING;
	public static final ModConfigSpec.ConfigValue<Boolean> MONSTER_SPAWN_WITH_MOUNT_ENABLED;
	public static final ModConfigSpec.ConfigValue<List<? extends String>> MOB_SPAWN_BLACKLIST;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_ATTRIBUTE_BONUS_START_DAY;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_ATTRIBUTE_BONUS_STOP_DAY;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_HEALTH_BONUS_MULTIPLIER;
	public static final ModConfigSpec.ConfigValue<String> ZOMBIE_HEALTH_BONUS_TYPE;
	public static final ModConfigSpec.ConfigValue<String> ZOMBIE_HEALTH_BONUS_DISTRIBUTION_MODE;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_ARMOR_BONUS_MULTIPLIER;
	public static final ModConfigSpec.ConfigValue<String> ZOMBIE_ARMOR_BONUS_TYPE;
	public static final ModConfigSpec.ConfigValue<String> ZOMBIE_ARMOR_BONUS_DISTRIBUTION_MODE;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_ATTACK_DAMAGE_BONUS_MULTIPLIER;
	public static final ModConfigSpec.ConfigValue<String> ZOMBIE_ATTACK_DAMAGE_BONUS_TYPE;
	public static final ModConfigSpec.ConfigValue<String> ZOMBIE_ATTACK_DAMAGE_BONUS_DISTRIBUTION_MODE;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_EQUIP_UNLOCK_DAY;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_WEAPON_BASE_CHANCE;
	public static final ModConfigSpec.ConfigValue<String> ZOMBIE_WEAPON_CHANCE_GROWTH_TYPE;

	public static final ModConfigSpec.ConfigValue<Double> FIRE_RESISTANT_ZOMBIE_SPAWN_START_DAY;
	public static final ModConfigSpec.ConfigValue<Double> FIRE_RESISTANT_ZOMBIE_BASE_SPAWN_CHANCE;
	public static final ModConfigSpec.ConfigValue<String> FIRE_RESISTANT_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE;
	public static final ModConfigSpec.ConfigValue<Boolean> FIRE_RESISTANCE_VISIBILITY;
	public static final ModConfigSpec.ConfigValue<Double> BLOCK_PLACING_ZOMBIE_SPAWN_START_DAY;
	public static final ModConfigSpec.ConfigValue<Double> BLOCK_PLACING_ZOMBIE_BASE_SPAWN_CHANCE;
	public static final ModConfigSpec.ConfigValue<String> BLOCK_PLACING_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_BLOCK_PLACE_COOLDOWN_TICKS;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_PLACE_MAX_COUNT;
	public static final ModConfigSpec.ConfigValue<List<? extends String>> ZOMBIE_POSSIBLE_BLOCKS;
	public static final ModConfigSpec.ConfigValue<Double> PICKAXE_ZOMBIE_SPAWN_START_DAY;
	public static final ModConfigSpec.ConfigValue<Double> PICKAXE_ZOMBIE_BASE_SPAWN_CHANCE;
	public static final ModConfigSpec.ConfigValue<String> PICKAXE_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_BLOCK_BREAK_COOLDOWN;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_DIGGING_BASE_EFFICIENCY;
	public static final ModConfigSpec.ConfigValue<List<? extends String>> BREAKABLE_BLOCK_TAGS;
	public static final ModConfigSpec.ConfigValue<List<? extends String>> BREAKABLE_BLOCK_LIST;
	public static final ModConfigSpec.ConfigValue<List<? extends String>> BREAKABLE_BLOCK_BLACKLIST;
	public static final ModConfigSpec.ConfigValue<Double> TNT_ZOMBIE_SPAWN_START_DAY;
	public static final ModConfigSpec.ConfigValue<Double> TNT_ZOMBIE_BASE_SPAWN_CHANCE;
	public static final ModConfigSpec.ConfigValue<String> TNT_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_TNT_PLACE_COOLDOWN_TICKS;
	public static final ModConfigSpec.ConfigValue<Boolean> ZOMBIE_TNT_INSTANT_IGNITION_ENABLED;
	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_TNT_MAX_COUNT;
	static {
		BUILDER.push("base_function_settings");
		ZOMBIE_ATTRIBUTE_BONUS_GLOBAL = BUILDER.comment("Other zombie enhancement settings will be invalid if disabled").define("zombieenhancementglobaltoggle", true);
		TELEPORT_REQUEST_FUNCTION = BUILDER.comment("Controls whether player can trigger teleport requests to other players").define("teleportrequestfunction", true);
		DISABLE_BABY_ZOMBIE_SPAWNING = BUILDER.comment("Whether baby zombies are banned").define("disablebabyzombiespawning", false);
		MONSTER_SPAWN_WITH_MOUNT_ENABLED = BUILDER.comment("Enables a chance for zombies to spawn with a mount when generated (use with caution as it is experimental).").define("monsterspawnwithmountenabled", false);
		MOB_SPAWN_BLACKLIST = BUILDER.comment("Mobs in this blacklist will never spawn").defineList("mobspawnblacklist", List.of("modid:registered_name1", "modid:registered_name2"), entry -> true);
		BUILDER.pop();
		BUILDER.push("zombie_attribute_growth_settings");
		ZOMBIE_ATTRIBUTE_BONUS_START_DAY = BUILDER.comment("Day when zombies start getting attribute bonuses - 0/negative number means bonuses start from day 1").define("zombieattributebonusstartday", (double) 5);
		ZOMBIE_ATTRIBUTE_BONUS_STOP_DAY = BUILDER.comment("Day when zombies stop getting attribute bonuses - 0/negative number means attribute bonuses never stop").define("zombieattributebonusstopday", (double) 100);
		BUILDER.push("max_health");
		ZOMBIE_HEALTH_BONUS_MULTIPLIER = BUILDER.comment("Zombie health bonus multiplier - 0/negative number disables health bonus").define("zombiehealthbonusmultiplier", (double) 0.5);
		ZOMBIE_HEALTH_BONUS_TYPE = BUILDER.comment("Zombie health bonus method - Optional values: linear, square, logarithm").define("zombiehealthbonustype", "linear");
		ZOMBIE_HEALTH_BONUS_DISTRIBUTION_MODE = BUILDER.comment("Optional values: all (all zombies get full bonus value), linear (linear distribution, max = bonus value), normal_distribution (normal distribution, max = bonus value)")
				.define("zombiehealthbonusdistribution", "normal_distribution");
		BUILDER.pop();
		BUILDER.push("armor");
		ZOMBIE_ARMOR_BONUS_MULTIPLIER = BUILDER.comment(" Zombie armor bonus multiplier - 0/negative number disables armor bonus").define("zombiearmorbonusmultiplier", (double) 0.1);
		ZOMBIE_ARMOR_BONUS_TYPE = BUILDER.comment("Zombie armor bonus method - Optional values: linear, square, logarithm").define("zombiearmorbonustype", "linear");
		ZOMBIE_ARMOR_BONUS_DISTRIBUTION_MODE = BUILDER.comment("Optional values: all (all zombies get full bonus value), linear (linear distribution, max = bonus value), normal_distribution (normal distribution, max = bonus value)")
				.define("zombiearmorbonusdistribution", "normal_distribution");
		BUILDER.pop();
		BUILDER.push("attack");
		ZOMBIE_ATTACK_DAMAGE_BONUS_MULTIPLIER = BUILDER.comment("Zombie attack damage bonus multiplier - 0/negative number disables attack damage bonus").define("zombieattackdamagebonusmultiplier", (double) 0.1);
		ZOMBIE_ATTACK_DAMAGE_BONUS_TYPE = BUILDER.comment("Zombie attack damage bonus method - Optional values: linear, square, logarithm").define("zombieattackdamagebonustype", "linear");
		ZOMBIE_ATTACK_DAMAGE_BONUS_DISTRIBUTION_MODE = BUILDER.comment("Optional values: all (all zombies get full bonus value), linear (linear distribution, max = bonus value), normal_distribution (normal distribution, max = bonus value)")
				.define("zombieattackdamagebonusdistribution", "normal_distribution");
		BUILDER.pop();
		BUILDER.push("equipment");
		ZOMBIE_EQUIP_UNLOCK_DAY = BUILDER.comment("The in-game day when zombies start spawning with equipment - 0/negative will disable this feature").define("zombieequipunlockday", (double) 7);
		ZOMBIE_WEAPON_BASE_CHANCE = BUILDER.comment("ase probability of a zombie spawning with a weapon on the unlock day - this is the starting chance before growth is applied").define("zombieweaponbasechance", (double) 0.1);
		ZOMBIE_WEAPON_CHANCE_GROWTH_TYPE = BUILDER.comment("Growth pattern for weapon spawn probability after the unlock day - Optional values: none, linear, inverse").define("zombieweaponchancegrowthtype", "linear");
		BUILDER.pop();
		BUILDER.pop();
		BUILDER.push("special_zombie_spawn_settings");
		BUILDER.push("fire-_resistant_zombies");
		FIRE_RESISTANT_ZOMBIE_SPAWN_START_DAY = BUILDER.comment("Day when fire-resistant zombies start spawning - 0/negative number disables this type of zombie").define("fireresistantzombiespawnstartday", (double) 30);
		FIRE_RESISTANT_ZOMBIE_BASE_SPAWN_CHANCE = BUILDER.comment("Base spawn chance of fire-resistant zombies").define("fireresistantzombiebasespawnchance", (double) 0.2);
		FIRE_RESISTANT_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE = BUILDER.comment("Spawn chance growth mode of fire-resistant zombies with days - Optional values: fixed, linear, square, logarithm").define("fireresistantzombiespawnchancegrowthtype", "linear");
		FIRE_RESISTANCE_VISIBILITY = BUILDER.comment("Whether the fire resistance particle effect is visibly shown on enhanced zombies").define("fireresistancevisibility", false);
		BUILDER.pop();
		BUILDER.push("block-holding_block-placing_zombies");
		BLOCK_PLACING_ZOMBIE_SPAWN_START_DAY = BUILDER.comment("Day when block-holding block-placing zombies start spawning - 0/negative number disables this type of zombie").define("blockplacingzombiespawnstartday", (double) 30);
		BLOCK_PLACING_ZOMBIE_BASE_SPAWN_CHANCE = BUILDER.comment("Base spawn chance of block-placing zombies").define("blockplacingzombiebasespawnchance", (double) 0.05);
		BLOCK_PLACING_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE = BUILDER.comment("Spawn chance growth mode of block-placing zombies with days - Optional values: fixed, linear, square, inverse").define("blockplacingzombiespawnchancegrowthtype", "linear");
		ZOMBIE_BLOCK_PLACE_COOLDOWN_TICKS = BUILDER.comment("Cooldown time (in ticks) between enhanced zombies attempting to place blocks").define("zombieblockplacecooldown", (double) 40);
		ZOMBIE_PLACE_MAX_COUNT = BUILDER.comment("Maximum number of blocks a block-holding zombie can place (must be ≥16; 0/negative mean infinite placement)").define("zombieplacemaxcount", (double) 64);
		ZOMBIE_POSSIBLE_BLOCKS = BUILDER.comment("List of valid block types that block-holding zombies can carry").defineList("zombiepossibleplacedblocks",
				List.of("minecraft:oak_planks", "minecraft:spruce_planks", "minecraft:birch_planks", "minecraft:jungle_planks", "minecraft:acacia_planks", "minecraft:dark_oak_planks", "minecraft:cherry_planks", "minecraft:mangrove_planks",
						"minecraft:bamboo_planks", "minecraft:crimson_planks", "minecraft:warped_planks", "minecraft:stone", "minecraft:granite", "minecraft:diorite", "minecraft:andesite", "minecraft:tuff", "minecraft:cobbled_deepslate",
						"minecraft:dirt", "minecraft:coarse_dirt", "minecraft:rooted_dirt", "minecraft:cobblestone", "minecraft:mossy_cobblestone", "minecraft:dripstone_block", "minecraft:sandstone"),
				entry -> true);
		BUILDER.pop();
		BUILDER.push("pickaxe-wielding_block-breaking_zombies");
		PICKAXE_ZOMBIE_SPAWN_START_DAY = BUILDER.comment("Day when pickaxe-wielding block-breaking zombies start spawning - 0/negative number disables this type of zombie").define("pickaxezombiespawnstartday", (double) 20);
		PICKAXE_ZOMBIE_BASE_SPAWN_CHANCE = BUILDER.comment("Base spawn chance of pickaxe-wielding zombies").define("pickaxezombiebasespawnchance", (double) 0.05);
		PICKAXE_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE = BUILDER.comment("Spawn chance growth mode of pickaxe-wielding zombies with days - Optional values: fixed, linear, square, inverse").define("pickaxezombiespawnchancegrowthtype", "linear");
		ZOMBIE_BLOCK_BREAK_COOLDOWN = BUILDER.comment("Cooldown time (in ticks) between enhanced zombies attempting to break blocks").define("zombieblockbreakcooldown", (double) 200);
		ZOMBIE_DIGGING_BASE_EFFICIENCY = BUILDER.comment("Base efficiency of zombies when breaking blocks (player efficiency is 1.0), not related to tool types but related to tool enchantments").define("zombiediggingbaseefficiency", (double) 0.5);
		BREAKABLE_BLOCK_TAGS = BUILDER.comment("The blocks zombies with pickaxe can break - tag").defineList("zombiebreakableblocktags",
				List.of("minecraft:mineable/axe", "minecraft:mineable/pickaxe", "minecraft:mineable/hoe", "minecraft:mineable/shovel", "minecraft:impermeable", "minecraft:dampens_vibrations"), entry -> true);
		BREAKABLE_BLOCK_LIST = BUILDER.comment("The blocks zombies with pickaxe can break - list").defineList("zombiebreakableblocklist", List.of("minecraft:glowstone", "minecraft:obsidian"), entry -> true);
		BREAKABLE_BLOCK_BLACKLIST = BUILDER.comment("The blocks zombies with pickaxe can never break - blacklist").defineList("zombiebreakableblockblacklist",
				List.of("minecraft:air", "minecraft:obsidian", "minecraft:crying_obsidian", "minecraft:netherite_block"), entry -> true);
		BUILDER.pop();
		BUILDER.push("tnt-holding_exploding_zombies");
		TNT_ZOMBIE_SPAWN_START_DAY = BUILDER.comment("Day when TNT-holding and detonating zombies start spawning - 0/negative number disables this type of zombie").define("tntzombiespawnstartday", (double) 50);
		TNT_ZOMBIE_BASE_SPAWN_CHANCE = BUILDER.comment("Base spawn chance of TNT-holding zombies").define("tntzombiebasespawnchance", (double) 0.05);
		TNT_ZOMBIE_SPAWN_CHANCE_GROWTH_TYPE = BUILDER.comment("Spawn chance growth mode of TNT-holding zombies with days - Optional values: fixed, linear, square, inverse").define("tntzombiespawnchancegrowthtype", "linear");
		ZOMBIE_TNT_PLACE_COOLDOWN_TICKS = BUILDER.comment("Cooldown time (in ticks) between enhanced zombies attempting to place TNT").define("zombietntplacecooldown", (double) 200);
		ZOMBIE_TNT_INSTANT_IGNITION_ENABLED = BUILDER.comment("If enabled, TNT placed by enhanced zombies will ignite immediately instead of having a normal fuse").define("zombietntinstantignition", true);
		ZOMBIE_TNT_MAX_COUNT = BUILDER.comment("Maximum number of tnt a tnt-holding zombie can place (must be ≥1; 0/negative  mean infinite placement)").define("zombietntmaxcount", (double) 8);
		BUILDER.pop();
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}