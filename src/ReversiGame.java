import java.util.Set;

/**
 * Created by carltidelius on 2018-01-19.
 */

public class ReversiGame {
	private GUI reversiGui;
	private ReversiPiece[][] gameBoard;

	public final static int BLANK = 0, WHITE = 1, BLACK = 2;

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
					gameBoard[i][j] = new ReversiPiece(WHITE);
				} else if ((i == 4 && j == 3) || (i == 3 && j == 4)) {
					gameBoard[i][j] = new ReversiPiece(BLACK);
				} else {
					gameBoard[i][j] = new ReversiPiece(BLANK);
				}
			}
		}
	}

	/*
	 * Check if the action the player made on GUI is legal?
	 */
	public boolean isLegalAction(int x, int y) {
		return true;
	}

	/*
	 * Apply the action that player made on GUI with XY coordinates?
	 */
	public void applyAction(int x, int y) {

	}

	/*
	 * Check the board to update GUI?
	 */
	public double evalState() {
		return 0;
	}

	/*
	 * See all possible actions for player
	 */
	public Set<ReversiPiece> getLegalActions(int color) {
		return null;
	}
}
