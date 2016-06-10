package client.CLI;

import java.util.Scanner;

public class InputOutput {

	private static Scanner input = new Scanner(System.in);

	/**
	 * private constructor, it can not be instantiated
	 */
	private InputOutput() {
	}

	public static void stampa(String s) {
		System.out.println(s);
	}

	public static void stampa(char c) {
		System.out.print(c);
	}

	public static void stampa(int i) {
		System.out.print(i);
	}

	public static String leggiStringa() {
		System.out.print("-> ");
		System.out.flush();
		return input.nextLine();
	}

	/**
	 * scans an integer from input and returns it , returns null if the
	 * parameter tornaIndietroDisponibile is true and the input is ..
	 * 
	 * @param tornaIndietroDisponibile
	 *            if true enables the option ".."
	 * @return the integer in input or null if the parameter
	 *         tornaIndietroDisponibile is true and the input is ..
	 */
	public static Integer leggiIntero(boolean tornaIndietroDisponibile) {
		String in;
		Integer scelta;
		System.out.print("--> ");
		System.out.flush();
		in = input.nextLine();
		if (in == ".." && tornaIndietroDisponibile)
			return null;
		try {
			scelta = Integer.parseInt(in);
			return scelta;
		} catch (NumberFormatException e) {
			stampa("Scelta non valida");
			return leggiIntero(tornaIndietroDisponibile);
		}
	}
}
