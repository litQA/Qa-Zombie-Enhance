package com.qa_zombie.void_main;

import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;

import com.qa_zombie.network.QaZombieModVariables;

@EventBusSubscriber
public class GameTickTimeCount {
	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent.Post event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		double tDay = 0;
		double tTime = 0;
		double modifiedDayCount = 0;
		tDay = world.dayTime() + 6000;
		modifiedDayCount = Math.floor(tDay / 24000) + 1;
		if (modifiedDayCount > QaZombieModVariables.MapVariables.get(world).dayCount) {
			QaZombieModVariables.MapVariables.get(world).dayCount = modifiedDayCount;
			QaZombieModVariables.MapVariables.get(world).markSyncDirty();
		}
		tTime = (tDay % 24000) / 1000;
		QaZombieModVariables.MapVariables.get(world).dayHour = Math.floor(tTime);
		QaZombieModVariables.MapVariables.get(world).dayMinute = Math.floor((tTime * 60) % 60);
		QaZombieModVariables.MapVariables.get(world).markSyncDirty();
	}
}