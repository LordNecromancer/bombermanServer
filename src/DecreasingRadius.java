import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasingRadius extends Poison implements Serializable {

    final private String type = "decreaseRadius";
    private static final long serialVersionUID = 1113799434508286969L;


    public DecreasingRadius() {
        super.type = type;
    }

    public void doYourThing(Player player) {
        if (player.bombRadius > 1)

            player.bombRadius--;
    }
}
