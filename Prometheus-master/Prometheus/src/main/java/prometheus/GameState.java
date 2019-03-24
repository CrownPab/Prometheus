package prometheus;

import java.util.Date;

import prometheus.constants.GlobalConstants;

public class GameState {
    public static int level;
    public static Date lastSaved;
    public static boolean hasUnsavedChanges;
    public static GlobalConstants.GameStatus gameStatus;
}
