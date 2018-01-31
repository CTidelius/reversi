/**
 * Created by carltidelius on 2018-01-29.
 */
public interface GameEnvironment {
    public void applyAction(int x, int y);
    public boolean isLegalAction(int x, int y);
    public void evalState();
}
