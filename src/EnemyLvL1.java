import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

class EnemyLvL1 extends Enemy implements Serializable {
    private static final long serialVersionUID = 1119342734505296969L;


    final private String type = "enemyLvL1";
    private int level = 1;
    static int sleep = 8;


    EnemyLvL1() {
        super.setLevel(level);
        super.setType(type);
        super.setSleep(sleep);
        super.setPassable(false);
        super.setNeverPassable(true);
        passableObjects.add("FieldCell");
        passableObjects.add("Player");

    }

    @Override
    void move(GameBoardCreator gameBoardCreator, int i, int j, ArrayList chooseDirection) {
        int randomNum = gameBoardCreator.enemyMove.getRandomDirection();
        if (randomNum != -1) {
            try {
                gameBoardCreator.movingEnemyRandomly.move(gameBoardCreator, i, j, this, randomNum, chooseDirection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
