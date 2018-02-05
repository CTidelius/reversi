package reversi;

public class BoardBuilder {

	public static void build1(ReversiGame game) {

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {

				if (y == 6) {
					game.addPiece(ReversiGame.WHITE, x, y);
				}
				if (x == 0) {
					game.addPiece(ReversiGame.WHITE, x, y);
				}
			}
		}
		game.addPiece(ReversiGame.BLACK, 7, 6);
		game.addPiece(ReversiGame.BLACK, 0, 0);
		game.addPiece(ReversiGame.BLANK, 0, 6);


	}

}
