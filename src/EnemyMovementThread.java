import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Sun on 07/08/2018.
 */
public class EnemyMovementThread extends Thread implements Serializable {

    GameBoardCreator gameBoardCreator;
    private static final long serialVersionUID = 1193799434508296969L;

    private GameComponent up;
    private GameComponent down;
    private GameComponent right;
    private GameComponent left;
    private MovingEnemyRandomly movingEnemyRandomly;
    private int k = 0;
    private ArrayList<GameComponent> chooseDirection = new ArrayList<>();
    private Map<Enemy, Integer> round = new HashMap<>();

    EnemyMovementThread(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
        movingEnemyRandomly = new MovingEnemyRandomly(gameBoardCreator);
    }

    @Override
    public void run() {

//        for (int p = 0; p < gameBoardCreator.enemies.size(); p++) {
//            round.put(gameBoardCreator.enemies.get(p), -1);
//        }

        int t = 0;
        while (true) {
            k = 0;
            t++;

            try {
                Thread.sleep(EnemyLvL1.sleep * 300);
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

                            k++;


                            if (enemy.getSleep() == EnemyLvL1.sleep) {
                                enemy.move(gameBoardCreator, i, j, chooseDirection);
                            }

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

        if (enemy.passableObjects.contains(up.getClass().getName())) {

            chooseDirection.add(up);
        }
        if (enemy.passableObjects.contains(right.getClass().getName())) {

            chooseDirection.add(right);
        }
        if (enemy.passableObjects.contains(down.getClass().getName())) {

            chooseDirection.add(down);
        }
        if (enemy.passableObjects.contains(left.getClass().getName())) {

            chooseDirection.add(left);
        }
    }


    private void moveEnemyLevelOne(EnemyLvL1 enemy, int i, int j) throws IOException {

//        int randomNum = getRandomDirection();
//        if (randomNum != -1) {
//            movingEnemyRandomly.move(gameBoardCreator, i, j, enemy, randomNum, chooseDirection);
//        }
    }


    Player findTarget(int i, int j) {
        if (gameBoardCreator.players.size() > 0) {
            Player player = null;
            int distanceX = gameBoardCreator.width;
            int distanceY = gameBoardCreator.height;
            for (int k = 0; k < gameBoardCreator.players.size(); k++) {
                if (gameBoardCreator.players.get(k).isAlive()) {
                    int interimX = Math.abs(i - gameBoardCreator.players.get(k).getPlayerPositionX());
                    int interimY = Math.abs(j - gameBoardCreator.players.get(k).getPlayerPositionY());

                    if (interimX + interimY < distanceX + distanceY) {
                        player = gameBoardCreator.players.get(k);
                        distanceX = interimX;
                        distanceY = interimY;
                    }
                }

            }

            return player;
        }
        return null;
    }


    int getRandomDirection() {

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
