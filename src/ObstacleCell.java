import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Sun on 05/31/2018.
 */
public class ObstacleCell extends GameComponent implements Serializable {

    private final String type = "obstacle";
    private static final long serialVersionUID = 1119442734515296969L;

    boolean hasDoor = false;
    PowerUps powerUps = null;
    Poison poison = null;
    GameBoardCreator gameBoardCreator = GameBoardCreator.getGameBoardCreator();

    public ObstacleCell() {
        super.setType(type);
        super.setPassable(false);


    }

    @Override
    public void destroy(Player player, int i, int j) {
        synchronized (gameBoardCreator.gameComponents) {
            ObstacleCell obstacleCell = this;
            try {
                gameBoardCreator.addScore(player, GameBoardCreator.obstacleScore);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (obstacleCell.hasDoor) {
                gameBoardCreator.gameComponents[i][j] = new Door();


            } else if (obstacleCell.powerUps != null) {
                gameBoardCreator.gameComponents[i][j] = obstacleCell.powerUps;


            } else if (obstacleCell.poison != null) {
                gameBoardCreator.gameComponents[i][j] = obstacleCell.poison;

            } else {
                gameBoardCreator.gameComponents[i][j] = new FieldCell();
            }
        }
    }
}
