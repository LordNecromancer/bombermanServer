import java.io.Serializable;


public class DecreasingBombsPoison extends Poison implements Serializable {
    final private String type = "decreaseBombs";
    private static final long serialVersionUID = 1113799434508676969L;


    DecreasingBombsPoison() {

        super.setType(type);
    }

    public void doYourThing(Player player) {
        if (player.getBombNum() > 1)

            player.setBombNum(player.getBombNum() - 1);
    }


}
