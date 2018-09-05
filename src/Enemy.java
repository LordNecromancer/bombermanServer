import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public abstract class Enemy extends GameComponent implements Serializable {
    private static final long serialVersionUID = 1114799734505296969L;

    private int level;
    private boolean isGhosting = false;
    private int sleep;
    private GameComponent disappearedObject = null;
    ArrayList<String> passableObjects = new ArrayList<>();
    GameBoardCreator gameBoardCreator = GameBoardCreator.getGameBoardCreator();

    Enemy() {
        super.setNeverPassable(true);
    }

    @Override
    public void destroy(Player player, int i, int j) {
        Enemy enemy = this;
        gameBoardCreator.enemyCount--;
        try {
            gameBoardCreator.addScore(player, 20 * enemy.getLevel());
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameBoardCreator.gameComponents[i][j] = new FieldCell();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isGhosting() {
        return isGhosting;
    }

    public void setGhosting(boolean ghosting) {
        isGhosting = ghosting;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public GameComponent getDisappearedObject() {
        return disappearedObject;
    }

    public void setDisappearedObject(GameComponent disappearedObject) {
        this.disappearedObject = disappearedObject;
    }
}
