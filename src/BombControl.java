import java.io.Serializable;


public class BombControl extends PowerUps implements Serializable {
    final private String type = "bombControl";
    private static final long serialVersionUID = 1113799434508676069L;


    CreatingGameBoard creatingGameBoard;
    Player player;

    public BombControl(CreatingGameBoard creatingGameBoard) {

        this.creatingGameBoard = creatingGameBoard;
        super.type = type;
        this.player = player;
    }

    public void doYourThing(Player player) {
        player.bombControl = true;
    }
}
