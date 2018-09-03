import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class LosingBombControl extends Poison implements Serializable {
    final private String type = "loseBombControl";
    private static final long serialVersionUID = 1113799434508346969L;


    public LosingBombControl() {

        super.setType(type);
    }

    public void doYourThing(Player player) {
        player.setBombControl(false);
    }


}
