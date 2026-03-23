package com.qa_zombie.tp_utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import com.qa_zombie.configuration.ZombieSettingsConfiguration;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class TeleportInvite {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		if (ZombieSettingsConfiguration.TELEPORT_REQUEST_FUNCTION.get()) {
			if ((commandParameterEntity(arguments, "player")) instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(((commandParameterEntity(arguments, "player")).getDisplayName().getString() + " " + Component.translatable("qa_zombie_util.info.tp_invite").getString()
						+ (commandParameterEntity(arguments, "player")).getDisplayName().getString())), false);
			{
				Entity _ent = (commandParameterEntity(arguments, "player"));
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(
							new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
									_ent.level().getServer(), _ent),
							("tellraw @s {\"text\":\"" + "" + Component.translatable("qa_zombie_util.info.agree").getString()
									+ "\",\"color\":\"blue\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/zombie_utli_teleport_to invite " + entity.getDisplayName().getString() + "\"}}"));
				}
			}
			(commandParameterEntity(arguments, "player")).getPersistentData().putDouble("invitedTick", 200);
			(commandParameterEntity(arguments, "player")).getPersistentData().putString("invitedPlayer", (entity.getDisplayName().getString()));
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("qa_zombie.info.funcion_disabled").getString())), false);
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