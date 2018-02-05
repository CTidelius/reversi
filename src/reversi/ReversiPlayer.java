package reversi;

public abstract class ReversiPlayer {

	/**
	 * Make a move as a specific color.
	 * 
	 * @param game
	 *            ReversiGame
	 * @param color
	 */
	public abstract void makeMove(ReversiGame game, int color);

}
