import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasingRadiusPoison extends Poison implements Serializable {

    final private String type = "decreaseRadius";
    private static final long serialVersionUID = 1113799434508286969L;


    DecreasingRadiusPoison() {
        super.setType(type);
    }

    public void doYourThing(Player player) {
        if (player.getBombRadius() > 1)

            player.setBombRadius(player.getBombRadius() - 1);
    }


}
