import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasingPointsPoison extends Poison implements Serializable {
    final private String type = "decreasePoints";
    private static final long serialVersionUID = 1113799434508276969L;


    GameBoardCreator gameBoardCreator;

    public DecreasingPointsPoison(GameBoardCreator gameBoardCreator) {
        super.setType(type);
        this.gameBoardCreator = gameBoardCreator;
    }

    public void doYourThing(Player player) throws IOException {

        gameBoardCreator.addScore(player, -100);

    }


}

