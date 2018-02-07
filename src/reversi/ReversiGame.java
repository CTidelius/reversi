package reversi;

import java.awt.Point;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import reversi.ReversiGame.BoardEvaluator;

/**
 * Created by carltidelius and Tom on 2018-01-19.
 */

public class ReversiGame {
	private ReversiPiece[][] gameBoard;

	public final static int BLANK = 0, WHITE = 1, BLACK = 2;

	/**
	 * Homogeneous heuristics. Counts occupied tiles that belongs to certain
	 * color.
	 */
	public final BoardEvaluator HOMOGENEOUS_HEURISTICS = new BoardEvaluator() {
		@Override
		public int evaluateGameState(ReversiGame game, int color) {

			int score = 0;

			for (int x = 0; x < 7; x++) {
				for (int y = 0; y < 7; y++) {
					if (game.gameBoard[x][y].getColor() == color) {
						score++;
					} else if (game.gameBoard[x][y].getColor() == ReversiGame.getInvertedColor(color)) {
						score--;
					}
				}
			}

			return score;
		}
	};

	/**
	 * Heuristics function used by Edax.
	 */
	public final BoardEvaluator EDAX_HEURISTICS = new BoardEvaluator() {

		@Override
		public int evaluateGameState(ReversiGame game, int color) {
			int score = 0;
			int c;

			if (isTerminalState()) {
				if (evalState(color, HOMOGENEOUS_HEURISTICS) < 0) {
					// If true, this means the opponent wins!!
					return Integer.MIN_VALUE;
				}
			}

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					c = game.gameBoard[x][y].getColor();
					if (c == color && isCorner(x, y)) {
						score += 3;
					} else if (c == color && isBuffer(x, y)) {
						score -= 1;
					} else if (c == color && isEdge(x, y)) {
						score += 2;
					} else if (c == BLANK) {
					} else if (c == color) {
						score++;
					}

					/*
					 * else if (c == getInvertedColor(color) && isCorner(x, y))
					 * { score -= 10; } else if (c == getInvertedColor(color) &&
					 * isBuffer(x, y)) { score += 10; } else if (c ==
					 * getInvertedColor(color) && isEdge(x, y)) { score -= 2; }
					 * else if (c == getInvertedColor(color)) { score--; } else
					 * { score++; }
					 * 
					 */
				}
			}
			return score;
		}
	};

	public ReversiGame() {
		gameBoard = new ReversiPiece[8][8];
		initializeBoard();

	}

	/**
	 * 
	 * @return Returns true if every game tile is occupied.
	 */
	private boolean isTerminalState() {

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {

				if (gameBoard[x][y].getColor() == BLANK) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean noMovesPossible() {
		if (getLegalActions(BLACK).size() == 0 && getLegalActions(WHITE).size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return Returns copy of this Reversi board.
	 */
	public ReversiGame copy() {

		ReversiGame new_game = new ReversiGame();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				new_game.addPiece(gameBoard[i][j].getColor(), i, j);
			}
		}

		return new_game;
	}

	/*
	 * Set up the game board with middle pieces as per Reversi rules
	 */
	private void initializeBoard() {
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

	public boolean isLegalAction(int color, int x, int y) {
		return traverse(color, x, y, false);
	}

	public boolean addNFlip(int color, int x, int y) {
		boolean can_flip = traverse(color, x, y, true);
		gameBoard[x][y].setColor(color);
		return can_flip;
	}

	private boolean traverse(int color, int x, int y, boolean isPlayerMove) {
		if (isOutOfBounds(x, y)) {
			return false;
		}
		if (gameBoard[x][y].getColor() != BLANK) {
			return false;
		}

		boolean isLegal = false;
		// For every possible direction.. (dx = [-1..1] and dy = [-1..1])
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {

				if (!(dx == 0 && dy == 0)) {

					if (checkDirection(color, x + dx, y + dy, dx, dy, 1, isPlayerMove)) {

						isLegal = true;

						if (!isPlayerMove) {
							return true;
						}
					}
				}
			}
		}

		return isLegal;
	}

	public static int getInvertedColor(int color) {
		return (color == BLACK) ? WHITE : BLACK;
	}

	private boolean checkDirection(int color, int x, int y, int dx, int dy, int distance, boolean isPlayerMove) {

		if (isOutOfBounds(x, y) || gameBoard[x][y].getColor() == BLANK) {
			return false;
		}

		if (gameBoard[x][y].getColor() == color && distance > 1) {
			// If the distance is over 1 and the same color is found again we
			// have surrounded the opponent.
			return true;
		} else if (gameBoard[x][y].getColor() == getInvertedColor(color)) {
			// If we find the opponent's color we continue traversing the board.
			boolean legalDirection = checkDirection(color, x + dx, y + dy, dx, dy, distance + 1, isPlayerMove);

			if (legalDirection && isPlayerMove) {
				gameBoard[x][y].flipPiece();
			}
			return legalDirection;

		} else {
			// If we find our own color and the distance is <1.
			return false;
		}

	}

	/*
	 * Apply the action that player made on GUI with XY coordinates?
	 */
	private void addPiece(int color, int x, int y) {
		gameBoard[x][y].setColor(color);
	}

	public int evalState(int color, BoardEvaluator heuristics) {
		return heuristics.evaluateGameState(this, color);

	}

	private boolean isCorner(int x, int y) {
		if (x == 0 && (y == 0 || y == 7) || x == 7 && (y == 0 || y == 7)) {
			return true;
		}

		return false;
	}

	private boolean isEdge(int x, int y) {
		if ((x == 0 && !isCorner(x, y) && !isBuffer(x, y)) || (y == 0 && !isCorner(x, y) && !isBuffer(x, y))
				|| (x == 7 && !isCorner(x, y) && !isBuffer(x, y)) || (y == 7 && !isCorner(x, y) && !isBuffer(x, y))) {
			return true;
		}
		return false;
	}

	private boolean isBuffer(int x, int y) {
		if (((x == 1 || x == 0) && (y == 0 || y == 1 || y == 6 || y == 7))
				|| ((x == 6 || x == 7) && (y == 6 || y == 7 || y == 0 || y == 1))) {
			return true;
		}
		return false;
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

	public int colorScore(int color) {
		int score = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard[i][j].getColor() == color) {
					score++;
				}
			}
		}
		return score;
	}

	public static void main(String[] args) {
		ReversiGame rg = new ReversiGame();
		Scanner input = new Scanner(System.in);

		int human_color = -1;

		while (true) {
			System.out.print("Choose Color (b/w): ");

			String color = input.nextLine();

			if (color.equals("b")) {
				human_color = BLACK;
				break;
			} else if (color.equals("w")) {
				human_color = WHITE;
				break;
			}
		}

		int ai_color = ReversiGame.getInvertedColor(human_color);
		long ai_time = 1;
		while (true) {
			System.out.print("Choose AI max calculation time (ms): ");

			try {
				ai_time = Integer.parseInt(input.nextLine());
				break;
			} catch (Exception e) {
			}
		}

	
		ReversiPlayer ai_player = new MinMaxAiPlayer(ai_time * 1000000l, rg.EDAX_HEURISTICS);
		ReversiPlayer human_player = new MinMaxAiPlayer(ai_time * 1000000l, rg.HOMOGENEOUS_HEURISTICS);

		boolean my_turn = true;
		while (true) {

			if (my_turn) {
				System.out.println(rg.getASCII(human_color));
				human_player.makeMove(rg, human_color);
			} else {

				long time = System.currentTimeMillis();
				ai_player.makeMove(rg, ai_color);
				long time_taken = System.currentTimeMillis() - time;

			}

			my_turn = !my_turn;

			if (rg.isTerminalState() || rg.noMovesPossible()) {
				break;
			}

		}
		
		System.out.println(rg.getASCII(human_color));

		System.out.println("*********");
		System.out.println("GAME OVER");
		System.out.println(human_player.toString() + ": " + rg.colorScore(human_color));
		System.out.println(ai_player.toString() + ": " + rg.colorScore(ai_color));

	}

	public String getASCII(int perspective) {

		final int COLOR = perspective;

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

				if (x == 7 && y == 1) {
					builder.append("  State evaluation (#)(Edax): " + evalState(BLACK, EDAX_HEURISTICS));
				} else if (x == 7 && y == 2) {
					builder.append("  State evaluation (O)(Edax): " + evalState(WHITE, EDAX_HEURISTICS));
				} else if (x == 7 && y == 4) {
					builder.append("\tO = WHITE");
				} else if (x == 7 && y == 5) {
					builder.append("\t# = BLACK");
				} else if (x == 7 && y == 6) {
					builder.append("\t+ = LEGAL MOVES");
				} else if (x == 7 && y == 7) {
					builder.append("\tScore  #=" + colorScore(BLACK) + " : O=" + colorScore(WHITE));
				}
			}

			builder.append("\n");
		}

		builder.append("y\n|\nv");

		return builder.toString();
	}

	public interface BoardEvaluator {

		/**
		 * 
		 * @param color
		 *            Color to evaluate for
		 * @return Returns state score for inserted color
		 */
		public int evaluateGameState(ReversiGame game, int color);

	}

}
