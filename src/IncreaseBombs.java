import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreaseBombs extends PowerUps implements Serializable {

    final private String type = "increaseBombs";
    private static final long serialVersionUID = 1113799434508306969L;


    public IncreaseBombs() {
        super.type = type;
    }

    public void doYourThing(Player player) {
        player.bombNum = player.bombNum + 1;
    }

}
