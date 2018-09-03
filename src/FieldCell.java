import java.io.Serializable;

/**
 * Created by Sun on 03/31/2018.
 */
public class FieldCell extends GameComponent implements Serializable {

    private final String type = "field";
    private static final long serialVersionUID = 1119342734515296969L;


    public FieldCell() {
        super.setType(type);
        super.setPassable(true);


    }

    @Override
    public void destroy(Player player, int i, int j) {

    }
}