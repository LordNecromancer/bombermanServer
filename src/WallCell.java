import java.io.Serializable;


public class WallCell extends GameComponent implements Serializable {

    private final String type = "wall";
    private static final long serialVersionUID = 1119742734515296969L;


    WallCell() {

        super.setType(type);
        super.setPassable(false);
        super.setExplosive(false);


    }

    @Override
    public void destroy(Player player, int i, int j) {

    }
}
