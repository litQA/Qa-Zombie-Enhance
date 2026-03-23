package com.qa_zombie.tp_utils;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerTickUpdate {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity.getPersistentData().getDouble("agreeTick") >= 1) {
			entity.getPersistentData().putDouble("agreeTick", (entity.getPersistentData().getDouble("agreeTick") - 1));
		}
		if (entity.getPersistentData().getDouble("invitedTick") >= 1) {
			entity.getPersistentData().putDouble("invitedTick", (entity.getPersistentData().getDouble("invitedTick") - 1));
		}
	}
}