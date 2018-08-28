import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreaseSpeed extends PowerUps implements Serializable {
    final private String type = "increaseSpeed";
    private static final long serialVersionUID = 1113799434508336969L;

    CreateBoard createBoard;
    Player player;

    public IncreaseSpeed(CreateBoard createBoard) {
        this.createBoard = createBoard;
        this.player = player;
        super.type = type;
    }

    public void doYourThing(Player player) {
        if (player.playerSpeed > 0)
            player.playerSpeed--;
    }
}