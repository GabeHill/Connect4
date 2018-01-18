package object;

public class ComPlayer extends Player {

	public ComPlayer(int num, char piece) {
		super("Comp" + num, piece);
	}

	public int getChoice(int num) {
		return (int) ((Math.random() * num) + 1);
	}
}
