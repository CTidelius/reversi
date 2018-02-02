package reversi;

import gui.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by carltidelius on 2018-01-19.
 */

public class ReversiGame {
    private GUI reversiGui;
    private ReversiPiece[][] gameBoard;

    public final static int BLANK = 0, WHITE = 1, BLACK = 2;

    public final static int UPLEFT = 0, UP = 1, UPRIGHT = 2, LEFT = 3,
            RIGHT = 4, DOWNLEFT = 5, DOWN = 6, DOWNRIGHT = 7;

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
    public boolean isLegalAction(int color, int x, int y) {
        if (isOutOfBounds(x, y)) {
            System.err.println("Outside bounds");
            System.exit(-1);
        }
        if (gameBoard[x][y].getColor() != BLANK) {
            return false;
        }
        boolean b = false;
        for(int i = 0; i < 8; i++){
            b =  checkDirection(i, new ReversiPiece(color, x, y));
        }
        return b;
    }

    public boolean checkDirection(int direction, ReversiPiece rp) {
        int x = 0;
        int y = 0;
        int color = 0;
        if (rp.getColor() == BLACK) {
            color = WHITE;
        } else
            color = BLACK;
        switch (direction) {
            case UPLEFT:
                x = rp.getX() - 1;
                y = rp.getY() - 1;
                if (isOutOfBounds(x, y)) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == BLANK) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == color) {
                    return checkDirection(direction, new ReversiPiece(rp.getColor(), x, y));
                } else {
                    return true;
                }
            case UP:
                x = rp.getX();
                y = rp.getY() - 1;
                if (isOutOfBounds(x, y)) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == BLANK) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == color) {
                    return checkDirection(direction, new ReversiPiece(rp.getColor(), x, y));
                } else {
                    return true;
                }
            case UPRIGHT:
                x = rp.getX() + 1;
                y = rp.getY() - 1;
                if (isOutOfBounds(x, y)) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == BLANK) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == color) {
                    return checkDirection(direction, new ReversiPiece(rp.getColor(), x, y));
                } else {
                    return true;
                }
            case LEFT:
                x = rp.getX() - 1;
                y = rp.getY();
                if (isOutOfBounds(x, y)) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == BLANK) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == color) {
                    return checkDirection(direction, new ReversiPiece(rp.getColor(), x, y));
                } else {
                    return true;
                }
            case RIGHT:
                x = rp.getX() + 1;
                y = rp.getY();
                if (isOutOfBounds(x, y)) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == BLANK) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == color) {
                    return checkDirection(direction, new ReversiPiece(rp.getColor(), x, y));
                } else {
                    return true;
                }
            case DOWNLEFT:
                x = rp.getX() - 1;
                y = rp.getY() + 1;
                if (isOutOfBounds(x, y)) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == BLANK) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == color) {
                    return checkDirection(direction, new ReversiPiece(rp.getColor(), x, y));
                } else {
                    return true;
                }

            case DOWN:
                x = rp.getX();
                y = rp.getY() + 1;
                if (isOutOfBounds(x, y)) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == BLANK) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == color) {
                    return checkDirection(direction, new ReversiPiece(rp.getColor(), x, y));
                } else {
                    return true;
                }

            case DOWNRIGHT:
                x = rp.getX() + 1;
                y = rp.getY() + 1;
                if (isOutOfBounds(x, y)) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == BLANK) {
                    return false;
                }
                if (gameBoard[x][y].getColor() == color) {
                    return checkDirection(direction, new ReversiPiece(rp.getColor(), x, y));
                } else {
                    return true;
                }

        }
        return false;
    }

    public Set<ReversiPiece> getPieces(int color) {
        Set<ReversiPiece> pieces = new HashSet<>(64);
        for (ReversiPiece[] rColumn : gameBoard) {
            for (ReversiPiece rp : rColumn) {
                if (rp.getColor() == color) {
                    pieces.add(rp);
                }
            }
        }
        return pieces;
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

    public boolean isOutOfBounds(int x, int y) {
        if (x > 7 || x < 0 || y > 7 || y < 0) return true;
        else return false;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("    !! Super Reversi !! \n");

        builder.append("   ");
        for (int x = 0; x < 8; x++) {
            builder.append(x);
            builder.append("  ");
        }

        builder.append("x -->");
        builder.append("\n");

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

                builder.append(p);
                builder.append("]");

            }

            builder.append("\n");
        }

        builder.append("y\n|\nv");
        builder.append("\n\tO = WHITE\n\t# = BLACK");

        return builder.toString();
    }
    public static void main(String[] args){
        ReversiGame rg = new ReversiGame();
        System.out.println(rg);
        System.out.println(rg.isLegalAction(BLACK, 2, 4));
    }
}
