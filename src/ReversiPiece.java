/**
 * Created by carltidelius on 2018-01-24.
 */
public class ReversiPiece {
    private int color;
    public ReversiPiece(int i){
        color = i;

    }
    public void flipPiece(){
        if(color != 0) {
            if (color == 1) {
                setColor((2));
            } else {
                setColor(1);
            }
        }
    }
    public void setColor(int x){
        color = x;
    }
    public int getColor(){
        return color;
    }
}
