package com.qa_zombie.tp_utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class Agree {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		Entity object = null;
		object = entity;
		if (object.getPersistentData().getDouble("agreeTick") >= 1 && ((commandParameterEntity(arguments, "player")).getDisplayName().getString()).equals(object.getPersistentData().getString("agreePlayer"))) {
			(commandParameterEntity(arguments, "player")).teleportTo((object.getX()), (object.getY()), (object.getZ()));
			if ((commandParameterEntity(arguments, "player")) instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((object.getDisplayName().getString() + " " + Component.translatable("qa_zombie_util.info.agree_tp_info").getString())), false);
			entity.getPersistentData().putDouble("agreeTick", 0);
			entity.getPersistentData().putString("agreePlayer", "");
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("qa_zombie_util.info.expired").getString())), false);
		}
	}

	private static Entity commandParameterEntity(CommandContext<CommandSourceStack> arguments, String parameter) {
		try {
			return EntityArgument.getEntity(arguments, parameter);
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}