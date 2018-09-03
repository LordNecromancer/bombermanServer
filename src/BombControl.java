import java.io.Serializable;


public class BombControl extends PowerUps implements Serializable {
    final private String type = "bombControl";
    private static final long serialVersionUID = 1113799434508676069L;


    GameBoardCreator gameBoardCreator;
    Player player;

    public BombControl(GameBoardCreator gameBoardCreator) {

        this.gameBoardCreator = gameBoardCreator;
        super.setType(type);
        this.player = player;
    }

    public void doYourThing(Player player) {
        player.setBombControl(true);
    }


}
