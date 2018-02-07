package reversi;

import java.util.Scanner;

public class HumanPlayer extends ReversiPlayer {

	@Override
	public void makeMove(ReversiGame game, int color) {
		Scanner input = new Scanner(System.in);
		System.out.println("Choose where to put your piece with x and y coordinates:" + '\n');

		if (game.getLegalActions(color).size() == 0) {
			System.out.println("No legal actions. Press ENTER to pass");
			input.nextLine();
			return;
		}
		
		while (true) {

			String s1 = input.next();
			char[] s = s1.toCharArray();
			int x = 0, y = 0;

			try {
				x = Character.getNumericValue(s[0]);
				y = Character.getNumericValue(s[1]);

			} catch (Exception e) {

			}

			if (game.isLegalAction(color, x, y)) {
				game.addNFlip(color, x, y);
				break;
			} else {
				System.out.println("This is not a legal action, chose one of the assigned legal moves.");
			}
		}
	}
	
	@Override
	public String toString() {
		return "Player";
	}

}
