package server.model;

import java.awt.Color;
import java.lang.reflect.Field;

public class ParseColor {
	
	/**
	 * 
	 * @param color
	 *            the integer value of the color
	 * @return the string of the color
	 */
	public static String colorIntToString(int color) {
		switch (color) {
		case -8355712:
			return "GRAY";
		case -256:
			return "YELLOW";
		case -16776961:
			return "BLUE";
		case -65536:
			return "RED";
		case -14336:
			return "ORANGE";
		case 16711935:
			return "MAGENTA";
		case -65281:
			return "MAGENTA";
		case 0:
			return "BLACK";
		case -16777216:
			return "BLACK";			
		case 16767215:
			return "WHITE";
		case -1:
			return "WHITE";
		case 16762880:
			return "ORANGE";
		case 65535:
			return "CYAN";
		case -16711681:
			return "CYAN";
		case -20561:
			return "PINK";
		case 16756655:
			return "PINK";
		default:
			return new String("Non definito nel parser: "+Integer.toString(color));
		}
	}

	/**
	 * 
	 * @param color
	 *            the string of the color
	 * @return the integer value of the color
	 */
	public static int colorStringToInt(String color) {
		switch (color.toUpperCase()) {
		case "MAGENTA":
			return 16711935;
		case "BLACK":
			return 0;
		case "WHITE":
			return 16767215;
		case "ORANGE":
			return 16762880;
		case "CYAN":
			return 65535;
		case "PINK":
			return 16756655;
		default:
			throw new IllegalStateException("il colore inserito non è corretto");
		}
	}

	/**
	 * 
	 * @param color
	 *            the integer value of the color
	 * @return the object color which has the value color
	 */
	public static Color colorIntToColor(int color) {
		return Color.getColor(null, color);
	}

	/**
	 * 
	 * @param color
	 *            the string of the color
	 * @return the object color which has the string color as name
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 *             if the color isn't correct
	 */
	public static Color colorStringToColor(String color) throws NoSuchFieldException {
		Field field;
		try {
			field = Color.class.getField(color.toLowerCase());
			return (Color) field.get(null);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}
	}

}
