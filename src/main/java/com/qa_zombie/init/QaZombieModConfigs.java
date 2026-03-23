package com.qa_zombie.init;

import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModContainer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import com.qa_zombie.configuration.ZombieSettingsConfiguration;

public class QaZombieModConfigs {
	@EventBusSubscriber
	public static class CommonRegistry {
		@SubscribeEvent
		public static void register(FMLConstructModEvent event) {
			event.enqueueWork(() -> {
				ModContainer container = ModList.get().getModContainerById("qa_zombie").get();
				container.registerConfig(ModConfig.Type.COMMON, ZombieSettingsConfiguration.SPEC, "Qa zombie enhancement.toml");
			});
		}
	}

	@EventBusSubscriber(value = Dist.CLIENT)
	public static class ClientRegistry {
		@SubscribeEvent
		public static void register(FMLConstructModEvent event) {
			event.enqueueWork(() -> {
				ModContainer container = ModList.get().getModContainerById("qa_zombie").get();
				container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
			});
		}
	}
}