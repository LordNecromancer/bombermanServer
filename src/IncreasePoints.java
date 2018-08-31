import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasePoints extends PowerUps implements Serializable {
    final private String type = "increasePoints";
    private static final long serialVersionUID = 1113799434508316969L;

    CreatingGameBoard creatingGameBoard;

    public IncreasePoints(CreatingGameBoard creatingGameBoard) {
        super.type = type;
        this.creatingGameBoard = creatingGameBoard;
    }

    public void doYourThing(Player player) throws IOException {
        creatingGameBoard.addScore(player, 100);
    }
}
