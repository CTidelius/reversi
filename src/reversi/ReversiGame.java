package reversi;

import java.awt.Point;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by carltidelius on 2018-01-19.
 */

public class ReversiGame {
	private ReversiPiece[][] gameBoard;
	private int YOURCOLOR = BLACK;

	public final static int BLANK = 0, WHITE = 1, BLACK = 2;

	
	public BoardHeuristics GREEDY_HEURISTICS = new BoardHeuristics() {
		@Override
		public int evaluateGameState(int color) {
			
			int score = 0;
			
			for (int x = 0; x < 7; x++) {
				for (int y = 0; y < 7; y++) {
					if (gameBoard[x][y].getColor() == color) {
						score++;
					} else if (gameBoard[x][y].getColor() == ReversiGame.getInvertedColor(color)) {
						score--;
					}
				}
			}

			return score;
		}
	};
	
	

	public ReversiGame() {
		gameBoard = new ReversiPiece[8][8];
		initializeBoard();

	}

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
	public boolean isLegalAction(int color, int x, int y, boolean isPlayerMove) {
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
	public void addPiece(int color, int x, int y) {
		gameBoard[x][y].setColor(color);
	}

	/*
	 * Check the board to update GUI?
	 */
	public int evalState(int color, BoardHeuristics heuristics) {
		return heuristics.evaluateGameState(color);

		/*
		int score = 0;
		int c;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				c = gameBoard[x][y].getColor();
				if (c == color && isCorner(x, y)) {
					score += 10;
				} else if (c == color && isBuffer(x, y)) {
					score -= 10;
				} else if (c == color && isEdge(x, y)) {
					score += 2;
				} else if (c == BLANK) {

				} else if (c == getInvertedColor(color) && isCorner(x, y)) {
					score -= 10;
				} else if (c == getInvertedColor(color) && isBuffer(x, y)) {
					score += 10;
				} else if (c == getInvertedColor(color) && isEdge(x, y)) {
					score -= 2;
				} else if (c == getInvertedColor(color)) {
					score--;
				} else {
					score++;
				}

			}
		}
		return score;
	
	*/
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
				if (isLegalAction(color, x, y, false)) {
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

		final int COLOR = YOURCOLOR;// TODO

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

			}

			builder.append("\n");
		}

		builder.append("y\n|\nv");
		builder.append("\n\tO = WHITE\n\t# = BLACK\n\t+ = LEGAL MOVES");

		return builder.toString();
	}

	public void getPlayerMove() {
		Scanner input = new Scanner(System.in);
		System.out.println("Choose where to put your piece with x and y coordinates:" + '\n');
		while (true) {
			String s1 = input.next();
			char[] s = s1.toCharArray();
			int x = 0, y = 0;
			try {
				x = Character.getNumericValue(s[0]);
				y = Character.getNumericValue(s[1]);
			} catch (Exception e) {

			}
			if (isLegalAction(YOURCOLOR, x, y, true)) {
				addPiece(YOURCOLOR, x, y);
				break;
			} else {
				System.out.println("This is not a legal action, chose one of the assigned legal moves.");
			}
		}
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

		/*
		 * 
		 * Scanner input = new Scanner(System.in);
		 * 
		 * while (true) { System.out.print("Choose Color (b/w): ");
		 * 
		 * String color = input.nextLine();
		 * 
		 * if (color.equals("b")){ rg.YOURCOLOR = BLACK; break; } else
		 * if(color.equals("w")) { rg.YOURCOLOR = WHITE; break; } }
		 * 
		 * 
		 * 
		 * 
		 * int time = 1; while (true) { System.out.print(
		 * "Choose AI max calculation time (ms): ");
		 * 
		 * try { time = Integer.parseInt(input.nextLine()); break; } catch
		 * (Exception e) { } } String player; while (true) { System.out.print(
		 * "Who do you want to play against? Other (P)layer, (A)I: ");
		 * 
		 * player = input.nextLine().toLowerCase();
		 * 
		 * if (player.equals("p")) { break; }else if (player.equals("a")){
		 * ReversiPlayer ai = new AiPlayer(1000);
		 * 
		 * break; } }
		 * 
		 */

		//ReversiPlayer ai = new AiPlayer(1000);
		
		ReversiPlayer ai = new GreedyAiPlayer();

		ReversiPlayer player = new HumanPlayer();

		boolean my_turn = true;
		while (true) {

			if (my_turn) {
				System.out.println(rg);
				player.makeMove(rg, ReversiGame.BLACK);
			} else {
				long time = System.currentTimeMillis();
				ai.makeMove(rg, ReversiGame.WHITE);
				long time_taken = System.currentTimeMillis() - time;
				System.err.println(time_taken);
			}

			my_turn = !my_turn;

		}

		/*
		 * boolean my_turn = true; while (true) {
		 * if(rg.getLegalActions(BLACK).size() == 0 &&
		 * rg.getLegalActions(WHITE).size() == 0){ int whiteScore =
		 * rg.colorScore(WHITE); int blackScore = rg.colorScore(BLACK);
		 * System.out.println("White player score: " + whiteScore);
		 * System.out.println("Black player score: " + blackScore);
		 * if(whiteScore<blackScore){ System.out.println("Black wins!"); }else
		 * if (whiteScore > blackScore){ System.out.println("White wins!");
		 * }else{ System.out.println("It's a tie!"); } break; }
		 * System.out.println(rg); if(player.equals("p")) { if (my_turn) { if
		 * (rg.getLegalActions(rg.YOURCOLOR).size() != 0){ rg.getPlayerMove(); }
		 * } else { rg.getPlayerMove(); } }else{ if (my_turn) { if
		 * (rg.getLegalActions(rg.YOURCOLOR).size() != 0){ rg.getPlayerMove(); }
		 * } else { if
		 * (rg.getLegalActions(rg.getInvertedColor(rg.YOURCOLOR)).size() != 0){
		 * ai.makeMove(5);
		 * 
		 * } } }
		 * 
		 * my_turn = !my_turn;
		 * 
		 * 
		 * 
		 * }
		 */

	}

	interface BoardHeuristics {

		/**
		 * 
		 * @param color
		 *            Color to evaluate for
		 * @return Returns state score for inserted color
		 */
		public int evaluateGameState(int color);

	}

}
