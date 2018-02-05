
package ai;

import java.awt.Point;
import java.util.Set;

import javax.sound.midi.Synthesizer;

import reversi.ReversiGame;

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
		Set<Point> legal_actions = GAME_BOARD.getLegalActions(AI_COLOR);

		int max_value = -Integer.MAX_VALUE;
		Point best_tile = null;

		for (Point tile : legal_actions) {

			ReversiGame game_copy = GAME_BOARD.copy();
			game_copy.addPiece(AI_COLOR, tile.x, tile.y);

			int branch_value = calculateBranchScore(game_copy, 0, max_ply_depth, false);

			if (branch_value >= max_value) {
				max_value = branch_value;
				best_tile = tile;
			}
		}

		if (best_tile == null) {
			// Do nothing
		} else {
			// Make calculated move.
			GAME_BOARD.isLegalAction(AI_COLOR, best_tile.x, best_tile.y, true);
			GAME_BOARD.addPiece(AI_COLOR, best_tile.x, best_tile.y);
		}

	}

	private int calculateBranchScore(ReversiGame simulated_game, int current_depth, int max_depth,
			boolean is_maximizer_turn) {
		// If max ply depth reached: evaluate state with heuristics.
		if (current_depth >= max_depth) {
			// TODO: change evalState to return integer.
			int state_score = simulated_game.evalState(AI_COLOR);

			// Return state score
			return state_score;

			// Else: keep simulating.
		} else {

			int current_color = (is_maximizer_turn) ? AI_COLOR : OPPONENT_COLOR;

			Set<Point> legal_actions = simulated_game.getLegalActions(current_color);

			int min_value = Integer.MAX_VALUE;
			int max_value = -Integer.MAX_VALUE;

			// For each legal action in current state...
			for (Point tile : legal_actions) {

				// Create a copy of the current game and add piece.
				ReversiGame game_copy = simulated_game.copy();
				game_copy.isLegalAction(current_color, tile.x, tile.y, true);
				game_copy.addPiece(current_color, tile.x, tile.y);

				int branch_value = calculateBranchScore(game_copy, current_depth + 1, max_depth, !is_maximizer_turn);
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
