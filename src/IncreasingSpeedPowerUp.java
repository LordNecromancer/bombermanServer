import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasingSpeedPowerUp extends PowerUps implements Serializable {
    final private String type = "increaseSpeed";
    private static final long serialVersionUID = 1113799434508336969L;

    GameBoardCreator gameBoardCreator;
    Player player;

    public IncreasingSpeedPowerUp(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
        this.player = player;
        super.setType(type);
    }

    public void doYourThing(Player player) {
        if (player.getPlayerSleep() > 0)
            player.setPlayerSleep(player.getPlayerSleep() - 1);
    }


}
