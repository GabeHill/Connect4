package game;

import enums.Direction;
import lib.ConsoleIO;
import object.ComPlayer;
import object.Player;

public class Game {
	private static char[][] board;
	private static int[] lastPlace = new int[2];
	private static Player[] players;

	private static boolean checkChar(int x, int y, char piece) {
		if ((x > board.length - 1 || y > board[x].length - 1) || (x < 0 || y < 0)) {
			return false;
		} else if ((board[x][y] == piece)) {
			return true;
		} else {
			return false;
		}
	}

	private static int checkNeighbor(char piece, int[] placed, Direction d) {
		if (placed[1] < board.length && placed[1] >= 0 && placed[0] >= 0 && placed[0] < board[placed[1]].length) {
			if (checkChar(placed[0], placed[1], piece)) {
				final int f = checkNeighbor(piece, new int[] { placed[0] + d.xMod, placed[1] + d.yMod }, d);
				return 1 + f;
			} else {
				return 0;
			}
		}
		return 0;
	}

	private static boolean checkWin(char piece) {
		final Direction[] dirs = Direction.values();
		for (final Direction d : dirs) {
			if (checkNeighbor(piece, lastPlace, d) > 3) {
				return true;
			}

		}

		return false;

	}

	private static Player genPlayer(int num, char piece) {
		final String nam = ConsoleIO.promptForInput("Enter player " + num + "'s name:", false);
		return new Player(nam, piece);
	}

	private static boolean init() {
		players = new Player[2];
		board = new char[][] { new char[] { '0', '0', '0', '0', '0', '0' }, new char[] { '0', '0', '0', '0', '0', '0' },
				new char[] { '0', '0', '0', '0', '0', '0' }, new char[] { '0', '0', '0', '0', '0', '0' },
				new char[] { '0', '0', '0', '0', '0', '0' }, new char[] { '0', '0', '0', '0', '0', '0' },
				new char[] { '0', '0', '0', '0', '0', '0' }, };
		final int choice = ConsoleIO
				.promptForMenuSelection(new String[] { "Human v Human", "Human v Com", "Com v Com" }, true);
		switch (choice) {
		case 1:
			// (int) (Math.random() * 2)
			players[0] = genPlayer(1, 'R');
			players[1] = genPlayer(2, 'Y');
			break;
		case 2:
			players[(int) (Math.random() * 2)] = genPlayer(1, 'R');
			players[(int) (Math.random() * 2)] = new ComPlayer(2, 'Y');
			break;
		case 3:
			players[(int) (Math.random() * 2)] = new ComPlayer(1, 'R');
			players[(int) (Math.random() * 2)] = new ComPlayer(2, 'Y');
			break;
		default:
			return false;
		}
		return true;
	}

	private static boolean placePiece(int column, char piece, int row) {
		if (!(row > board.length - 1)) {

			if (board[row][column] == '0') {
				board[row][column] = piece;
				lastPlace[0] = row;
				lastPlace[1] = column;
				return true;
			} else {
				return placePiece(column, piece, row + 1);
			}
		} else {
			return false;
		}
	}

	private static void printBoard() {
		for (final char[] cs : board) {
			for (final char c : cs) {
				System.out.print(c);
			}
			System.out.println();
		}
	}

	public static void run() {
		boolean play = true;
		do {
			if (!init()) {
				play = false;
			} else {
				Player winner = null;
				do {
					winner = turn();
				} while (winner == null);
				System.out.println(winner.name + " wins.");
				play = ConsoleIO.promptForBool("Would you like to go again? y/n", "y", "n");
			}
		} while (play);
	}

	private static Player turn() {
		for (final Player player : players) {
			printBoard();
			System.out.println(player.name + "'s turn.");
			while (!placePiece(player.getChoice("What column? 1-6", 1, 6) - 1, player.piece, 0)) {
				System.out.println("That column is full.");
			}
			if (checkWin(player.piece)) {
				return player;
			}
		}

		return null;
	}

}
