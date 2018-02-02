package reversi;

import java.awt.Point;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import gui.GUI;

/**
 * Created by carltidelius on 2018-01-19.
 */

public class ReversiGame {
	private GUI reversiGui;
	private ReversiPiece[][] gameBoard;

	public final static int BLANK = 0, WHITE = 1, BLACK = 2;

	public final static int UPLEFT = 0, UP = 1, UPRIGHT = 2, LEFT = 3, RIGHT = 4, DOWNLEFT = 5, DOWN = 6, DOWNRIGHT = 7;

	public ReversiGame() {
		reversiGui = new GUI();
		gameBoard = new ReversiPiece[8][8];
		initializeBoard();
	}

	public ReversiGame copy() {
		return null;
	}

	/*
	 * Set up the game board with middle pieces as per Reversi rules
	 */
	public void initializeBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
					gameBoard[i][j] = new ReversiPiece(WHITE, i, j);
				} else if ((i == 4 && j == 3) || (i == 3 && j == 4)) {
					gameBoard[i][j] = new ReversiPiece(BLACK, i, j);
				} else {
					gameBoard[i][j] = new ReversiPiece(BLANK, i, j);
				}
			}
		}
	}

	/*
	 * Check if the action the player made on GUI is legal?
	 */
	public boolean isLegalAction(int color, int x, int y) {
		if (isOutOfBounds(x, y)) {
			System.err.println("Outside bounds");
			System.exit(-1);
		}
		if (gameBoard[x][y].getColor() != BLANK) {
			return false;
		}

		// For every possible direction.. (dx = [-1..1] and dy = [-1..1])
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {

				if (!(dx == 0 && dy == 0)) {
					if (checkDirection(color, x + dx, y + dy, dx, dy, 1)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private int getInvertedColor(int color) {
		return (color == BLACK) ? WHITE : BLACK;
	}

	private boolean checkDirection(int color, int x, int y, int dx, int dy, int distance) {

		if (isOutOfBounds(x, y) || gameBoard[x][y].getColor() == BLANK) {
			return false;
		}

		if (gameBoard[x][y].getColor() == color && distance > 1) {
			// If the distance is over 1 and the same color is found again we
			// have surrounded the opponent.
			return true;
		} else if (gameBoard[x][y].getColor() == getInvertedColor(color)) {
			// If we find the opponent's color we continue traversing the board.
			return checkDirection(color, x + dx, y + dy, dx, dy, distance + 1);
		} else {
			// If we find our own color and the distance is <1.
			return false;
		}

	}

	/*
	 * Apply the action that player made on GUI with XY coordinates?
	 */
	public void addPiece(int color, int x, int y) {
		gameBoard[x][y].setColor(color);
	}

	/*
	 * Check the board to update GUI?
	 */
	public double evalState(int color) {
		return 0;
	}

	/*
	 * See all possible actions for player
	 */
	public Set<Point> getLegalActions(int color) {

		Set<Point> legal = new HashSet<>();

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (isLegalAction(color, x, y)) {
					legal.add(new Point(x, y));
				}
			}
		}
		return legal;
	}

	public boolean isOutOfBounds(int x, int y) {
		if (x > 7 || x < 0 || y > 7 || y < 0)
			return true;
		else
			return false;
	}

	@Override
	public String toString() {

		final int COLOR = BLACK;

		StringBuilder builder = new StringBuilder();
		builder.append("    !! Super Reversi !! \n");

		builder.append("   ");
		for (int x = 0; x < 8; x++) {
			builder.append(x);
			builder.append("  ");
		}

		builder.append("x -->");
		builder.append("\n");

		Set<Point> legal_moves = getLegalActions(COLOR);
		System.out.println("Legal moved: " + legal_moves.size());

		for (int y = 0; y < 8; y++) {

			builder.append(y);
			builder.append(" ");

			for (int x = 0; x < 8; x++) {

				builder.append("[");

				String p = " ";
				if (gameBoard[x][y].getColor() == WHITE) {
					p = "O";
				} else if (gameBoard[x][y].getColor() == BLACK) {
					p = "#";
				}

				for (Point point : legal_moves) {
					if (point.x == x && point.y == y) {
						p = "+";
					}
				}

				builder.append(p);
				builder.append("]");

			}

			builder.append("\n");
		}

		builder.append("y\n|\nv");
		builder.append("\n\tO = WHITE\n\t# = BLACK\n\t+ = LEGAL MOVES");

		return builder.toString();
	}

	public static void main(String[] args) {
		ReversiGame rg = new ReversiGame();
		rg.addPiece(WHITE, 2, 3);

		Scanner input = new Scanner(System.in);

		while (true) {
			System.out.print("Choose Color (b/w): ");

			String color = input.nextLine();

			if (color.equals("b") || color.equals("w")) {
				break;
			}
		}

		while (true) {
			System.out.print("Choose AI max calculation time (ms): ");

			try {
				int time = Integer.parseInt(input.nextLine());
				break;
			} catch (Exception e) {
			}
		}

		System.out.println(rg);
	}
}
