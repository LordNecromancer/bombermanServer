import java.io.Serializable;


public class Door extends GameComponent implements Serializable {
    private static final long serialVersionUID = 1114742734505296969L;

    final private String type = "door";

    public Door() {
        super.setType(type);
        super.setExplosive(false);
        super.setNeverPassable(true);


    }


    @Override
    public void destroy(Player player, int i, int j) {

    }
}
