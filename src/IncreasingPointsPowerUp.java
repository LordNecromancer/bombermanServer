import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasingPointsPowerUp extends PowerUps implements Serializable {
    final private String type = "increasePoints";
    private static final long serialVersionUID = 1113799434508316969L;

    GameBoardCreator gameBoardCreator;

    IncreasingPointsPowerUp(GameBoardCreator gameBoardCreator) {
        super.setType(type);
        this.gameBoardCreator = gameBoardCreator;
    }

    public void doYourThing(Player player) throws IOException {
        gameBoardCreator.addScore(player, 100);
    }

}
