package reversi;

import java.awt.Point;
import java.util.Set;

public class GreedyAiPlayer extends ReversiPlayer {

	@Override
	public void makeMove(ReversiGame game, int color) {

		Set<Point> legal_actions = game.getLegalActions(color);

		Point best_action = null;
		int max_score = -Integer.MIN_VALUE;

		for (Point action : legal_actions) {

			// For each legal action evaluate
			ReversiGame simulated_game = game.copy();
			simulated_game.addNFlip(color, action.x, action.y);

			int score = simulated_game.evalState(color, game.HOMOGENEOUS_HEURISTICS);

			if (score >= max_score) {
				max_score = score;
				best_action = action;
			}
		}

		if (best_action == null) {
			// Do nothing
		} else {
			game.addNFlip(color, best_action.x, best_action.y);

		}
	}
	
	@Override
	public String toString() {
		return "Greedy Ai";
	}

}
