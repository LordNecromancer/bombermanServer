import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class EnemyMovementThreadTypeTwo extends Thread implements Serializable {


    GameBoardCreator gameBoardCreator;
    private static final long serialVersionUID = 1183799434508296969L;

    private GameComponent up;
    private GameComponent down;
    private GameComponent right;
    private GameComponent left;
    private MovingEnemyRandomly movingEnemyRandomly ;
    private int k = 0;
    private ArrayList<GameComponent> chooseDirection = new ArrayList<>();
    private Map<Enemy, Integer> round = new HashMap<>();

    EnemyMovementThreadTypeTwo(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
        movingEnemyRandomly = new MovingEnemyRandomly(gameBoardCreator);
    }

    @Override
    public void run() {

        for (int p = 0; p < gameBoardCreator.enemies.size(); p++) {
            round.put(gameBoardCreator.enemies.get(p), -1);
        }

        int t = 0;
        while (true) {
            k = 0;
            t++;


            try {
                Thread.sleep(EnemyLvL3.sleep * 300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for (int p = 0; p < gameBoardCreator.enemies.size(); p++) {
                int k = 0;
                Enemy enemy = gameBoardCreator.enemies.get(p);
                for (int i = 0; i < gameBoardCreator.width + 2; i++) {
                    for (int j = 0; j < gameBoardCreator.height + 2 && k < 1; j++) {
                        if (gameBoardCreator.gameComponents[i][j] == enemy) {


                            findPossibleDirections(enemy, i, j);

                            if (enemy.getSleep() == EnemyLvL3.sleep) {


                                    enemy.move(gameBoardCreator, i, j,chooseDirection);


                            }
                            k++;
                        }

                    }
                }
            }
        }
    }

    private void findPossibleDirections(Enemy enemy, int i, int j) {
        chooseDirection = new ArrayList<>();

        up = gameBoardCreator.gameComponents[i - 1][j];
        right = gameBoardCreator.gameComponents[i][j + 1];
        down = gameBoardCreator.gameComponents[i + 1][j];
        left = gameBoardCreator.gameComponents[i][j - 1];

        if (enemy.passableObjects.contains(up.getClass().getName()) && !up.isNeverPassable()) {

            chooseDirection.add(up);
        }
        if (enemy.passableObjects.contains(right.getClass().getName()) && !right.isNeverPassable()) {

            chooseDirection.add(right);
        }
        if (enemy.passableObjects.contains(down.getClass().getName()) && !down.isNeverPassable()) {

            chooseDirection.add(down);
        }
        if (enemy.passableObjects.contains(left.getClass().getName()) && !left.isNeverPassable()) {

            chooseDirection.add(left);
        }
    }


    private void moveEnemyLevelThree(Enemy enemy, int i, int j) throws IOException {
//
//        Player player = gameBoardCreator.enemyMove.findTarget(i, j);
//        if (player != null) {
//            synchronized (gameBoardCreator.gameComponents) {
//                if (round.get(enemy) == -1) {
//
//                    if (up.getType().equals("player") || down.getType().equals("player") || right.getType().equals("player") || left.getType().equals("player")) {
//
//                        gameBoardCreator.gameComponents[player.getPlayerPositionX()][player.getPlayerPositionY()] = enemy;
//                        gameBoardCreator.gameComponents[i][j] = new FieldCell();
//                        gameBoardCreator.killPlayer(player);
//                    }
//
//                    if (player.getPlayerPositionX() > i && chooseDirection.contains(down)) {
//
//
//                        if (enemy.isGhosting()) {
//
//                            currentLocation((EnemyLvL4) enemy, i, j);
//                            nextLocation((EnemyLvL4) enemy, i + 1, j);
//                        } else {
//                            gameBoardCreator.gameComponents[i][j] = new FieldCell();
//
//                        }
//                        gameBoardCreator.gameComponents[i + 1][j] = enemy;
//
//                    } else if (player.getPlayerPositionX() < i && chooseDirection.contains(up)) {
//
//                        if (enemy.isGhosting()) {
//
//                            currentLocation((EnemyLvL4) enemy, i, j);
//                            nextLocation((EnemyLvL4) enemy, i - 1, j);
//                        } else {
//                            gameBoardCreator.gameComponents[i][j] = new FieldCell();
//
//                        }
//                        gameBoardCreator.gameComponents[i - 1][j] = enemy;
//
//                    } else if (player.getPlayerPositionY() > j && chooseDirection.contains(right)) {
//
//                        if (enemy.isGhosting()) {
//
//                            currentLocation((EnemyLvL4) enemy, i, j);
//                            nextLocation((EnemyLvL4) enemy, i, j + 1);
//                        } else {
//                            gameBoardCreator.setGameComponents(i, j, new FieldCell());
//
//                        }
//                        gameBoardCreator.gameComponents[i][j + 1] = enemy;
//
//                    } else if (player.getPlayerPositionY() < j && chooseDirection.contains(left)) {
//
//                        if (enemy.isGhosting()) {
//
//                            currentLocation((EnemyLvL4) enemy, i, j);
//                            nextLocation((EnemyLvL4) enemy, i, j - 1);
//                        } else {
//                            gameBoardCreator.setGameComponents(i, j, new FieldCell());
//
//                        }
//                        gameBoardCreator.setGameComponents(i, j - 1, enemy);
//                    } else {
//                        round.put(enemy, 0);
//                    }
//                } else {
//
//                    round.put(enemy, round.get(enemy) + 1);
//                    int randomNum = getRandomDirection();
//                    movingEnemyRandomly.move(gameBoardCreator, i, j, enemy, randomNum, chooseDirection);
//                    if (round.get(enemy) == 9) {
//                        round.put(enemy, -1);
//                    }
//                }
//            }
//        }
    }


     void nextLocation(Enemy enemy, int i, int j) {
        if (!gameBoardCreator.gameComponents[i][j].isNeverPassable()) {

            enemy.setDisappearedObject(gameBoardCreator.gameComponents[i][j]);
        }
    }

     void currentLocation(Enemy enemy, int i, int j) {


        gameBoardCreator.setGameComponents(i, j, enemy.getDisappearedObject());
        enemy.setDisappearedObject(new FieldCell());


    }


    public int getRandomDirection() {

        Random random = new Random();

        if (chooseDirection.size() == 0) {
            return -1;
        }
        if (chooseDirection.size() == 1) {
            return 0;
        } else {
            int randomNum = random.nextInt(chooseDirection.size());

            return randomNum;
        }
    }
}


