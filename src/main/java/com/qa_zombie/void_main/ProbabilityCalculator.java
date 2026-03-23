package com.qa_zombie.void_main;

public class ProbabilityCalculator {
	public static double execute(double base, double count_day, String bonus_type) {
		if (bonus_type == null)
			return 0;
		String type = "";
		double addedAttribute = 0;
		double attributeCountDay = 0;
		double baseChance = 0;
		attributeCountDay = Math.pow(count_day, 0.8);
		baseChance = base;
		type = bonus_type;
		if ((type).equals("square")) {
			addedAttribute = baseChance * Math.pow(attributeCountDay, 2);
		} else if ((type).equals("fixed")) {
			addedAttribute = baseChance;
		} else if ((type).equals("inverse")) {
			addedAttribute = baseChance * (1 - 1 / attributeCountDay);
		} else {
			addedAttribute = baseChance * attributeCountDay;
		}
		return addedAttribute;
	}
}