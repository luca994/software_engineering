package client.CLI;

import java.util.Scanner;

public class InputOutput {

	private static Scanner input = new Scanner(System.in);

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
}
