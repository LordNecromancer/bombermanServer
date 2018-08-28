import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreaseSpeed extends Poison implements Serializable {
    private static final long serialVersionUID = 1113799434508296969L;

    final private String type = "decreaseSpeed";
    CreateBoard createBoard;
    Player player;

    public DecreaseSpeed(CreateBoard createBoard) {
        this.createBoard = createBoard;
        super.type = type;
        this.player = player;
    }

    public void doYourThing(Player player) {
        if (player.playerSpeed > 0)
            player.playerSpeed++;
    }
}
