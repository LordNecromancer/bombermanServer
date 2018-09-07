import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

class EnemyLvL3 extends Enemy implements Serializable {
    private static final long serialVersionUID = 1193799634508296969L;

    final private String type = "enemyLvL3";
    private int level = 3;
    static int sleep = 4;
    private int round = -1;

    @Override
    void move(GameBoardCreator gameBoardCreator, int i, int j, ArrayList chooseDirection) {

        GameComponent up = gameBoardCreator.gameComponents[i - 1][j];
        GameComponent right = gameBoardCreator.gameComponents[i][j + 1];
        GameComponent down = gameBoardCreator.gameComponents[i + 1][j];
        GameComponent left = gameBoardCreator.gameComponents[i][j - 1];

        Player player = gameBoardCreator.enemyMove.findTarget(i, j);
        if (player != null) {
            synchronized (gameBoardCreator.gameComponents) {
                if (round == -1) {

                    if (chooseDirection.contains(player)) {

                        gameBoardCreator.gameComponents[player.getPlayerPositionX()][player.getPlayerPositionY()] = this;
                        gameBoardCreator.gameComponents[i][j] = this.getDisappearedObject();
                        try {
                            gameBoardCreator.killPlayer(player);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (player.getPlayerPositionX() > i && chooseDirection.contains(down)) {


                        if (this.isGhosting()) {

                            gameBoardCreator.enemyMove2.currentLocation(this, i, j);
                            gameBoardCreator.enemyMove2.nextLocation(this, i + 1, j);
                        } else {
                            gameBoardCreator.gameComponents[i][j] = new FieldCell();

                        }
                        gameBoardCreator.gameComponents[i + 1][j] = this;

                    } else if (player.getPlayerPositionX() < i && chooseDirection.contains(up)) {

                        if (this.isGhosting()) {

                            gameBoardCreator.enemyMove2.currentLocation(this, i, j);
                            gameBoardCreator.enemyMove2.nextLocation(this, i - 1, j);
                        } else {
                            gameBoardCreator.gameComponents[i][j] = new FieldCell();

                        }
                        gameBoardCreator.gameComponents[i - 1][j] = this;

                    } else if (player.getPlayerPositionY() > j && chooseDirection.contains(right)) {

                        if (this.isGhosting()) {

                            gameBoardCreator.enemyMove2.currentLocation(this, i, j);
                            gameBoardCreator.enemyMove2.nextLocation(this, i, j + 1);
                        } else {
                            gameBoardCreator.setGameComponents(i, j, new FieldCell());

                        }
                        gameBoardCreator.gameComponents[i][j + 1] = this;

                    } else if (player.getPlayerPositionY() < j && chooseDirection.contains(left)) {

                        if (this.isGhosting()) {

                            gameBoardCreator.enemyMove2.currentLocation(this, i, j);
                            gameBoardCreator.enemyMove2.nextLocation(this, i, j - 1);
                        } else {
                            gameBoardCreator.setGameComponents(i, j, new FieldCell());

                        }
                        gameBoardCreator.setGameComponents(i, j - 1, this);
                    } else {
                        round = 0;
                    }
                } else {

                    round++;
                    int randomNum = gameBoardCreator.enemyMove2.getRandomDirection();
                    try {
                        gameBoardCreator.movingEnemyRandomly.move(gameBoardCreator, i, j, this, randomNum, chooseDirection);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (round == 9) {
                        round = -1;
                    }
                }
            }
        }
    }

    EnemyLvL3() {
        super.setLevel(level);
        super.setType(type);
        super.setSleep(sleep);
        super.setPassable(false);
        super.setNeverPassable(true);
        passableObjects.add("FieldCell");
        passableObjects.add("Player");


    }


}
