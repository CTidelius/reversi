import java.util.Set;

/**
 * Created by carltidelius on 2018-01-29.
 */
public class OpponentAI {

	private final int AI_COLOR;
	private final int OPPONENT_COLOR;
	private final ReversiGame GAME_BOARD;

	public OpponentAI(int color, ReversiGame game) {
		this.AI_COLOR = color;
		this.GAME_BOARD = game;

		OPPONENT_COLOR = (AI_COLOR == ReversiGame.BLACK) ? ReversiGame.WHITE : ReversiGame.BLACK;
	}

	public void makeMove(int max_ply_depth) {
		Set<ReversiPiece> legal_actions = GAME_BOARD.getLegalActions(AI_COLOR);

		int max_value = -Integer.MAX_VALUE;
		ReversiPiece best_move = null;

		for (ReversiPiece piece : legal_actions) {

			ReversiGame game_copy = GAME_BOARD.copy();
			game_copy.addPiece(piece);
			int branch_value = calculateBranchScore(game_copy, 0, max_ply_depth, false);

			if (branch_value >= max_value) {
				max_value = branch_value;
				best_move = piece;
			}
		}

		GAME_BOARD.addPiece(best_move);

	}

	private int calculateBranchScore(ReversiGame simulated_game, int current_depth, int max_ply_depth,
			boolean is_maximizer_turn) {

		// If max ply depth reached: evaluate state with heuristics.
		if (current_depth * 2 != max_ply_depth) {

			// TODO: change evalState to return integer.
			int state_score = (int) simulated_game.evalState();

			// Return state score
			return state_score;

			// Else: keep simulating.
		} else {

			int current_color = (is_maximizer_turn) ? AI_COLOR : OPPONENT_COLOR;

			Set<ReversiPiece> legal_actions = simulated_game.getLegalActions(current_color);

			int min_value = Integer.MAX_VALUE;
			int max_value = -Integer.MAX_VALUE;

			// For each legal action in current state...
			for (ReversiPiece piece : legal_actions) {

				// Create a copy of the current game and add piece.
				ReversiGame game_copy = simulated_game.copy();
				game_copy.addPiece(piece);
				int branch_value = calculateBranchScore(game_copy, current_depth + 1);

				// Update min/max values
				if (branch_value >= max_value) {
					max_value = branch_value;
				} else if (branch_value <= min_value) {
					min_value = branch_value;
				}
			} // END OF LOOP

			// Determine what value to return based on who's turn it is.
			if (is_maximizer_turn) {
				is_maximizer_turn = !is_maximizer_turn;
				return max_value;
			} else {
				is_maximizer_turn = !is_maximizer_turn;
				return min_value;
			}

		}

	}

}
