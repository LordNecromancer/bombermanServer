import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasingSpeedPoison extends Poison implements Serializable {
    private static final long serialVersionUID = 1113799434508296969L;

    final private String type = "decreaseSpeed";
    GameBoardCreator gameBoardCreator;
    Player player;

    public DecreasingSpeedPoison(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
        super.setType(type);
        this.player = player;
    }

    public void doYourThing(Player player) {
        if (player.getPlayerSleep() > 0)
            player.setPlayerSleep(player.getPlayerSleep() + 1);
    }


}
