/**
 * Created by carltidelius on 2018-01-29.
 */
public interface GameEnvironment {
    void applyAction(int x, int y);
    boolean isLegalAction(int x, int y);
    void evalState();
}
