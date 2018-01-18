package object;

import lib.ConsoleIO;

public class Player {
	public final String name;
	public final char piece;

	public Player(String name, char piece) {
		this.piece = piece;
		this.name = name;
	}

	public int getChoice(String prompt, int min, int max) {
		return ConsoleIO.promptForInt(prompt, min, max);
	}
}
