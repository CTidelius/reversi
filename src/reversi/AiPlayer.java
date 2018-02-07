
package reversi;

import java.awt.Point;
import java.util.Set;

import javax.sound.midi.Synthesizer;

public class AiPlayer extends ReversiPlayer {

	private final long GLOBAL_TIME_LIMIT;

	public AiPlayer(long time_limit) {
		this.GLOBAL_TIME_LIMIT = time_limit;
	}

	@Override
	public void makeMove(ReversiGame board, int ai_color) {
		long start_time = System.nanoTime();

		Set<Point> legal_actions = board.getLegalActions(ai_color);
		if (legal_actions.size() == 0)
			return;

		int max_value = -Integer.MAX_VALUE;
		Point best_tile = null;
		long time_limit = GLOBAL_TIME_LIMIT / legal_actions.size();
		long done_by = start_time + time_limit;
		for (Point tile : legal_actions) {

			ReversiGame game_copy = board.copy();
			game_copy.addPiece(ai_color, tile.x, tile.y);

			int branch_value = calculateBranchScore(game_copy, false, ai_color, done_by, time_limit);

			if (branch_value >= max_value) {
				max_value = branch_value;
				best_tile = tile;
			}
			done_by += time_limit;

		}

		if (best_tile == null) {
			// No move to make, do nothing
		} else {
			// Make calculated best move.

			// TODO: CHANGE name of isLegalAction, make method add last piece =>
			// no need to use addPiece here.
			board.isLegalAction(ai_color, best_tile.x, best_tile.y, true);
			board.addPiece(ai_color, best_tile.x, best_tile.y);
		}

	}

	private int calculateBranchScore(ReversiGame simulated_game, boolean is_maximizer_turn, int ai_color, long done_by,
			long time_limit) {

		long start_time = System.nanoTime();

		if (System.nanoTime() >= done_by - 12000) {

			int state_score = simulated_game.evalState(ai_color, simulated_game.EDAX_HEURISTICS);

			// Return state score
			return state_score;

			// Else: keep simulating.
		} else {

			int current_sim_color = (is_maximizer_turn) ? ai_color : ReversiGame.getInvertedColor(ai_color);

			Set<Point> legal_actions = simulated_game.getLegalActions(current_sim_color);
			if (legal_actions.size() == 0) {
				return simulated_game.evalState(ai_color, simulated_game.EDAX_HEURISTICS);
			}

			int min_value = Integer.MAX_VALUE;
			int max_value = -Integer.MAX_VALUE;

			// For each legal action in current state...
			time_limit = time_limit / legal_actions.size();
			done_by = start_time + time_limit;
			for (Point tile : legal_actions) {

				// Create a copy of the current game and add piece.
				ReversiGame game_copy = simulated_game.copy();

				// TODO: CHANGE name of isLegalAction, make method add last
				// piece => no need to use addPiece here.
				game_copy.isLegalAction(current_sim_color, tile.x, tile.y, true);
				game_copy.addPiece(current_sim_color, tile.x, tile.y);

				int branch_value = calculateBranchScore(game_copy, !is_maximizer_turn, ai_color, done_by, time_limit);
				done_by += time_limit;
				// Update min/max values
				if (branch_value >= max_value) {
					max_value = branch_value;
				} else if (branch_value <= min_value) {
					min_value = branch_value;
				}
			} // END OF LOOP

			// Determine what value to return based on who's turn it is.
			if (is_maximizer_turn) {
				return max_value;
			} else {
				return min_value;
			}

		}

	}

}
