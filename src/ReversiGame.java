/**
 * Created by carltidelius on 2018-01-19.
 */

public class ReversiGame{
    private GUI reversiGui;
    private ReversiPiece[][] gameBoard;

    public ReversiGame(){
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
    /*
    * Check if the action the player made on GUI is legal?
     */
    public boolean isLegalAction(int x, int y){
        return true;
    }
    /*
    * Apply the action that player made on GUI with XY coordinates?
     */
    public void applyAction(int x, int y){

    }
    /*
    * Check the board to update GUI?
     */
    public void evalState(){

    }
    /*
    * See all possible actions for player
     */
    public int[][] possMoves(){
       return new int[8][8];
    }
}
