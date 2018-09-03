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
    private MovingEnemyRandomly movingEnemyRandomly = new MovingEnemyRandomly();
    private int k = 0;
    private ArrayList<GameComponent> chooseDirection = new ArrayList<>();
    private Map<Enemy, Integer> round = new HashMap<>();

    EnemyMovementThreadTypeTwo(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
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


                            chooseDirection = new ArrayList<>();

                            up = gameBoardCreator.gameComponents[i - 1][j];
                            right = gameBoardCreator.gameComponents[i][j + 1];
                            down = gameBoardCreator.gameComponents[i + 1][j];
                            left = gameBoardCreator.gameComponents[i][j - 1];
                            if (enemy.isGhosting()) {
                                if (i - 1 > 0 && !(gameBoardCreator.gameComponents[i - 1][j].isNeverPassable())) {
                                    up.setPassable(true);
                                }
                                if (i + 1 > 0 && !(gameBoardCreator.gameComponents[i + 1][j].isNeverPassable())) {
                                    down.setPassable(true);
                                }
                                if (j - 1 > 0 && !(gameBoardCreator.gameComponents[i][j - 1].isNeverPassable())) {

                                    left.setPassable(true);
                                }
                                if (j + 1 > 0 && !(gameBoardCreator.gameComponents[i][j + 1].isNeverPassable())) {
                                    right.setPassable(true);
                                }
                            } else {


                                if (up.getPassable()) {

                                    chooseDirection.add(up);

                                }
                                if (right.getPassable()) {

                                    chooseDirection.add(right);

                                }
                                if (down.getPassable()) {

                                    chooseDirection.add(down);

                                }
                                if (left.getPassable()) {


                                    chooseDirection.add(left);

                                }
                            }

//
//                             else {
//                                if (up.passable && !up.type.equals("wall") && !up.type.equals("obstacle")) {
//
//                                    chooseDirection.add(up);
//                                }
//                                if (right.passable && !right.type.equals("wall") && !right.type.equals("obstacle")) {
//
//                                    chooseDirection.add(right);
//                                }
//                                if (down.passable && !down.type.equals("wall") && !down.type.equals("obstacle")) {
//
//                                    chooseDirection.add(down);
//                                }
//                                if (left.passable && !left.type.equals("wall") && !left.type.equals("obstacle")) {
//
//                                    chooseDirection.add(left);
//                                }
//                            }
                            if (enemy.getSleep() == EnemyLvL3.sleep) {

                                try {
                                    moveEnemyLevelThree((Enemy) gameBoardCreator.gameComponents[i][j], i, j);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            k++;
                        }

                    }
                }
            }
        }
    }


    private void moveEnemyLevelThree(Enemy enemy, int i, int j) throws IOException {

        Player player = gameBoardCreator.enemyMove.findTarget(i, j);
        if (player != null) {
            synchronized (gameBoardCreator.gameComponents) {
                if (round.get(enemy) == -1) {

                    if (up.getType().equals("player") || down.getType().equals("player") || right.getType().equals("player") || left.getType().equals("player")) {

                        gameBoardCreator.gameComponents[player.getPlayerPositionX()][player.getPlayerPositionY()] = enemy;
                        gameBoardCreator.gameComponents[i][j] = new FieldCell();
                        gameBoardCreator.killPlayer(player);
                    }

                    if (player.getPlayerPositionX() > i && chooseDirection.contains(down)) {


                        if (enemy.isGhosting()) {

                            nextLocation((EnemyLvL4) enemy, i, j);
                            currentLocation((EnemyLvL4) enemy, i + 1, j);
                        } else {
                            gameBoardCreator.gameComponents[i][j] = new FieldCell();

                        }
                        gameBoardCreator.gameComponents[i + 1][j] = enemy;

                    } else if (player.getPlayerPositionX() < i && chooseDirection.contains(up)) {

                        if (enemy.isGhosting()) {

                            nextLocation((EnemyLvL4) enemy, i, j);
                            currentLocation((EnemyLvL4) enemy, i - 1, j);
                        } else {
                            gameBoardCreator.gameComponents[i][j] = new FieldCell();

                        }
                        gameBoardCreator.gameComponents[i - 1][j] = enemy;

                    } else if (player.getPlayerPositionY() > j && chooseDirection.contains(right)) {

                        if (enemy.isGhosting()) {

                            nextLocation((EnemyLvL4) enemy, i, j);
                            currentLocation((EnemyLvL4) enemy, i, j + 1);
                        } else {
                            gameBoardCreator.setGameComponents(i, j, new FieldCell());

                        }
                        gameBoardCreator.gameComponents[i][j + 1] = enemy;

                    } else if (player.getPlayerPositionY() < j && chooseDirection.contains(left)) {

                        if (enemy.isGhosting()) {

                            nextLocation((EnemyLvL4) enemy, i, j);
                            currentLocation((EnemyLvL4) enemy, i, j - 1);
                        } else {
                            gameBoardCreator.setGameComponents(i, j, new FieldCell());

                        }
                        gameBoardCreator.setGameComponents(i, j - 1, enemy);
                    } else {
                        round.put(enemy, 0);
                    }
                } else {

                    round.put(enemy, round.get(enemy) + 1);
//                    if (enemy.type.equals("enemyLvL4")) {
//                        if (up.type.equals("wall") || up.type.equals("obstacle")) {
//                            up.passable = false;
//                            chooseDirection.remove(up);
//                        }
//                        if (down.type.equals("wall") || down.type.equals("obstacle")) {
//                            down.passable = false;
//                            chooseDirection.remove(down);
//                        }
//                        if (right.type.equals("wall") || right.type.equals("obstacle")) {
//                            right.passable = false;
//                            chooseDirection.remove(right);
//                        }
//                        if (left.type.equals("wall") || left.type.equals("obstacle")) {
//                            left.passable = false;
//                            chooseDirection.remove(left);
//                        }
//                    }
                    int randomNum = getRandomDirection();
                    movingEnemyRandomly.move(gameBoardCreator, i, j, enemy, randomNum, chooseDirection);
                    if (round.get(enemy) == 9) {
                        round.put(enemy, -1);
                    }
                }
            }
        }
    }


    private void currentLocation(Enemy enemy, int i, int j) {
        if (!gameBoardCreator.gameComponents[i][j].isNeverPassable()) {

            enemy.setDisappearedObject(gameBoardCreator.gameComponents[i][j]);
        }
    }

    private void nextLocation(EnemyLvL4 enemy, int i, int j) {

        enemy.disappearedObject.setPassable(false);

        gameBoardCreator.setGameComponents(i, j, enemy.disappearedObject);
        gameBoardCreator.gameComponents[i][j].setPassable(false);
        enemy.disappearedObject = null;


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


