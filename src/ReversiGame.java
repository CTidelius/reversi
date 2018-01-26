/**
 * Created by carltidelius on 2018-01-19.
 */

public class ReversiGame {
    private GUI reversiGui;
    private ReversiPiece[][] gameBoard;

    public ReversiGame(){
        reversiGui = new GUI();
        gameBoard = new ReversiPiece[8][8];
        initializeBoard();
    }

    /** Set up the gameboard with middle pieces as usual
     */
    public void initializeBoard(){
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if ((i == 3 && j == 3) || (i == 4 && j == 4)){
                    gameBoard[i][j] = new ReversiPiece(1);
                }else if ((i == 4 && j == 3) || (i == 3 && j == 4)){
                    gameBoard[i][j] = new ReversiPiece(2);
                }else{
                    gameBoard[i][j] = new ReversiPiece(0);
                }
            }
        }
    }
    public boolean checkLegalMove(ReversiPiece[][] gb){

    }

}
