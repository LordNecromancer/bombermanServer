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

    CreatingGameBoard creatingGameBoard;
    private static final long serialVersionUID = 1193799434508296969L;

    private GameComponent up;
    private GameComponent down;
    private GameComponent right;
    private GameComponent left;
    private MoveEnemyRandomly moveEnemyRandomly = new MoveEnemyRandomly();
    int k = 0;
    private ArrayList<GameComponent> chooseDirection = new ArrayList<>();
    ;
    private Map<Enemy, Integer> round = new HashMap<>();

    EnemyMovementThread(CreatingGameBoard creatingGameBoard) {
        this.creatingGameBoard = creatingGameBoard;
    }

    @Override
    public void run() {

        for (int p = 0; p < creatingGameBoard.enemies.size(); p++) {
            round.put(creatingGameBoard.enemies.get(p), -1);
        }

        int t = 0;
        while (true) {
            k = 0;
            t++;

            try {
                Thread.sleep(EnemyLvL1.sleep * 300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int p = 0; p < creatingGameBoard.enemies.size(); p++) {
                int k = 0;
                Enemy enemy = creatingGameBoard.enemies.get(p);
                for (int i = 0; i < creatingGameBoard.width + 2; i++) {
                    for (int j = 0; j < creatingGameBoard.height + 2 && k < 1; j++) {
                        if (creatingGameBoard.gameComponents[i][j] == enemy) {


                            findPossibleDirections(i, j);

                            k++;


                            if (enemy.type.equals("enemyLvL1")) {

                                try {
                                    moveEnemyLevelOne((EnemyLvL1) creatingGameBoard.gameComponents[i][j], i, j);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            if (enemy.type.equals("enemyLvL2")) {
                                try {
                                    moveEnemyLevelTwo((EnemyLvL2) enemy, i, j);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void findPossibleDirections(int i, int j) {
        chooseDirection = new ArrayList<>();

        up = creatingGameBoard.gameComponents[i - 1][j];
        right = creatingGameBoard.gameComponents[i][j + 1];
        down = creatingGameBoard.gameComponents[i + 1][j];
        left = creatingGameBoard.gameComponents[i][j - 1];

        if (up.passable) {

            chooseDirection.add(up);
        }
        if (right.passable) {

            chooseDirection.add(right);
        }
        if (down.passable) {

            chooseDirection.add(down);
        }
        if (left.passable) {

            chooseDirection.add(left);
        }
    }


    private void moveEnemyLevelOne(EnemyLvL1 enemy, int i, int j) throws IOException {

        int randomNum = getRandomDirection();
        if (randomNum != -1) {
            moveEnemyRandomly.move(creatingGameBoard, i, j, enemy, randomNum, chooseDirection);
        }
    }


    private void moveEnemyLevelTwo(EnemyLvL2 enemy, int i, int j) throws IOException {


        if (round.get(enemy) == -1) {
            moveEnemyLevelTwoSmartly(enemy, i, j);
        } else {

            round.put(enemy, round.get(enemy) + 1);
            int randomNum = getRandomDirection();
            if (randomNum != -1) {
                moveEnemyRandomly.move(creatingGameBoard, i, j, enemy, randomNum, chooseDirection);
            }
            if (round.get(enemy) == 9) {
                round.put(enemy, -1);
            }
        }
    }

    private void moveEnemyLevelTwoSmartly(EnemyLvL2 enemy, int i, int j) throws IOException {
        Player player = findTarget(i, j);
        if (player != null) {
            synchronized (creatingGameBoard.gameComponents) {
                if (up.type.equals("player") || down.type.equals("player") || right.type.equals("player") || left.type.equals("player")) {
                    creatingGameBoard.gameComponents[player.playerPositionX][player.playerPositionY] = enemy;
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();
                    creatingGameBoard.killPlayer(player);
                }
                if (player.playerPositionX > i && chooseDirection.contains(down)) {

                    creatingGameBoard.gameComponents[i + 1][j] = enemy;
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();


                } else if (player.playerPositionX < i && chooseDirection.contains(up)) {

                    creatingGameBoard.gameComponents[i - 1][j] = enemy;
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();

                } else if (player.playerPositionY > j && chooseDirection.contains(right)) {

                    creatingGameBoard.gameComponents[i][j + 1] = enemy;
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();

                } else if (player.playerPositionY < j && chooseDirection.contains(left)) {

                    creatingGameBoard.gameComponents[i][j - 1] = enemy;
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();

                } else {
                    round.put(enemy, 0);
                    moveEnemyRandomly.move(creatingGameBoard, i, j, enemy, getRandomDirection(), chooseDirection);
                }
            }
        }
    }


    Player findTarget(int i, int j) {
        if (creatingGameBoard.players.size() > 0) {
            Player player = creatingGameBoard.players.get(0);
            int distanceX = Math.abs(player.playerPositionX - i);
            int distanceY = Math.abs(player.playerPositionY - j);
            for (int k = 0; k < creatingGameBoard.players.size(); k++) {
                int interimX = Math.abs(i - creatingGameBoard.players.get(k).playerPositionX);
                int interimY = Math.abs(j - creatingGameBoard.players.get(k).playerPositionY);

                if (interimX + interimY < distanceX + distanceY) {
                    player = creatingGameBoard.players.get(k);
                    distanceX = interimX;
                    distanceY = interimY;
                }

            }

            return player;
        }
        return null;
    }


    private int getRandomDirection() {

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
