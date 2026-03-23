package com.qa_zombie.void_main;

import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import java.util.Random;

public class AttributeCalculator {
	public static double execute(double base, double count_day, String bonus_type, String distribution_mode) {
		if (bonus_type == null || distribution_mode == null)
			return 0;
		String type = "";
		double addedAttribute = 0;
		double attributeCountDay = 0;
		double addedBase = 0;
		attributeCountDay = count_day;
		addedBase = base;
		type = bonus_type;
		if ((type).equals("square")) {
			addedAttribute = addedBase * Math.pow(attributeCountDay, 2);
		} else if ((type).equals("logarithm")) {
			addedAttribute = addedBase * Math.log(attributeCountDay);
		} else if ((type).equals("inverse")) {
			addedAttribute = addedBase * (1 - 1 / attributeCountDay);
		} else {
			addedAttribute = addedBase * attributeCountDay;
		}
		type = distribution_mode;
		if ((type).equals("linear")) {
			addedAttribute = Mth.nextDouble(RandomSource.create(), 0, addedAttribute);
		} else if ((type).equals("all")) {
			addedAttribute = addedAttribute;
		} else {
			addedAttribute = addedAttribute * new Random().nextGaussian();
		}
		return addedAttribute;
	}
}