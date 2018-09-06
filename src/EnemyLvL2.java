import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL2 extends Enemy implements Serializable {
    private static final long serialVersionUID = 1193799734508296969L;

    final private String type = "enemyLvL2";
    private int level = 2;
    static int sleep = 8;
    private int round=-1;

    @Override
    void move(GameBoardCreator gameBoardCreator, int i, int j, ArrayList chooseDirection) {
        try {
            if (round == -1) {

                moveEnemyLevelTwoSmartly( i, j,chooseDirection);

            } else {

                round++;
                int randomNum = gameBoardCreator.enemyMove.getRandomDirection();
                if (randomNum != -1) {

                    gameBoardCreator.movingEnemyRandomly.move(gameBoardCreator, i, j, this, randomNum, chooseDirection);
                }
            }

            if (round == 9) {
                round = -1;
            }
        } catch (IOException e)

        {
            e.printStackTrace();
        }

    }


    private void moveEnemyLevelTwoSmartly( int i, int j,ArrayList chooseDirection) throws IOException {
        Player player = gameBoardCreator.enemyMove.findTarget(i, j);
       GameComponent up = gameBoardCreator.gameComponents[i - 1][j];
        GameComponent right = gameBoardCreator.gameComponents[i][j + 1];
        GameComponent down = gameBoardCreator.gameComponents[i + 1][j];
        GameComponent left = gameBoardCreator.gameComponents[i][j - 1];

        if (player != null) {
            synchronized (gameBoardCreator.gameComponents) {
                if (chooseDirection.contains(player)) {

                    gameBoardCreator.gameComponents[player.getPlayerPositionX()][player.getPlayerPositionY()] = this;
                    gameBoardCreator.gameComponents[i][j] = new FieldCell();
                    gameBoardCreator.killPlayer(player);
                }
               else if (player.getPlayerPositionX() > i && chooseDirection.contains(down)) {

                    gameBoardCreator.gameComponents[i + 1][j] = this;
                    gameBoardCreator.gameComponents[i][j] = new FieldCell();


                } else if (player.getPlayerPositionX() < i && chooseDirection.contains(up)) {

                    gameBoardCreator.gameComponents[i - 1][j] = this;
                    gameBoardCreator.gameComponents[i][j] = new FieldCell();

                } else if (player.getPlayerPositionY() > j && chooseDirection.contains(right)) {

                    gameBoardCreator.gameComponents[i][j + 1] = this;
                    gameBoardCreator.gameComponents[i][j] = new FieldCell();

                } else if (player.getPlayerPositionY() < j && chooseDirection.contains(left)) {

                    gameBoardCreator.gameComponents[i][j - 1] = this;
                    gameBoardCreator.gameComponents[i][j] = new FieldCell();

                } else {
                    round=0;
                    gameBoardCreator.movingEnemyRandomly.move(gameBoardCreator, i, j, this, gameBoardCreator.enemyMove.getRandomDirection(), chooseDirection);
                }
            }
        }
    }

    public EnemyLvL2() {
        super.setLevel(level);
        super.setType(type);
        super.setSleep(sleep);
        super.setPassable(false);
        super.setNeverPassable(true);
        passableObjects.add("FieldCell");
        passableObjects.add("Player");


    }
}
