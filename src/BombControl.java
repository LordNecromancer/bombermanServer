import java.io.Serializable;


public class BombControl extends PowerUps implements Serializable {
    final private String type = "bombControl";
    private static final long serialVersionUID = 1113799434508676069L;


    CreateBoard createBoard;
    Player player;

    public BombControl(CreateBoard createBoard) {

        this.createBoard = createBoard;
        super.type = type;
        this.player = player;
    }

    public void doYourThing(Player player) {
        player.bombControl = true;
    }
}
