import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasingSpeed extends Poison implements Serializable {
    private static final long serialVersionUID = 1113799434508296969L;

    final private String type = "decreaseSpeed";
    CreatingGameBoard creatingGameBoard;
    Player player;

    public DecreasingSpeed(CreatingGameBoard creatingGameBoard) {
        this.creatingGameBoard = creatingGameBoard;
        super.type = type;
        this.player = player;
    }

    public void doYourThing(Player player) {
        if (player.playerSpeed > 0)
            player.playerSpeed++;
    }
}
