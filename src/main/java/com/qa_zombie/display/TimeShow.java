package com.qa_zombie.display;

import net.minecraft.world.level.LevelAccessor;

import com.qa_zombie.network.QaZombieModVariables;

public class TimeShow {
	public static String execute(LevelAccessor world) {
		double hour = 0;
		double minute = 0;
		String iHour = "";
		String iMinute = "";
		hour = QaZombieModVariables.MapVariables.get(world).dayHour;
		minute = QaZombieModVariables.MapVariables.get(world).dayMinute;
		if (hour <= 9) {
			iHour = "0" + Math.round(hour);
		} else {
			iHour = "" + Math.round(hour);
		}
		if (minute <= 9) {
			iMinute = "0" + Math.round(minute);
		} else {
			iMinute = "" + Math.round(minute);
		}
		return "Day " + Math.round(QaZombieModVariables.MapVariables.get(world).dayCount) + "\u4E28Time " + iHour + ":" + iMinute;
	}
}