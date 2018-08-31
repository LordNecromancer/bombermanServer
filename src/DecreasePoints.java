import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasePoints extends Poison implements Serializable {
    final private String type = "decreasePoints";
    private static final long serialVersionUID = 1113799434508276969L;


    CreatingGameBoard creatingGameBoard;

    public DecreasePoints(CreatingGameBoard creatingGameBoard) {
        super.type = type;
        this.creatingGameBoard = creatingGameBoard;
    }

    public void doYourThing(Player player) throws IOException {

        creatingGameBoard.addScore(player, -100);

    }

}

