import java.io.Serializable;


public class DecreasingBombs extends Poison implements Serializable {
    final private String type = "decreaseBombs";
    private static final long serialVersionUID = 1113799434508676969L;


    public DecreasingBombs() {

        super.type = type;
    }

    public void doYourThing(Player player) {
        if (player.bombNum > 1)

            player.bombNum = player.bombNum - 1;
    }
}