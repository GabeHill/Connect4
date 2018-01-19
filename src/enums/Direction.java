package enums;

public enum Direction {
	DOWN(0, -1), DOWN_LEFT(-1, -1), DOWN_RIGHT(1, -1), RIGHT(1, 0);

	public final int xMod;
	public final int yMod;

	private Direction(int x, int y) {
		xMod = x;
		yMod = y;
	}
}
