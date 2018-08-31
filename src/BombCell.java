import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Sun on 03/31/2018.
 */
public class BombCell extends Cell implements Serializable {

    private final String type = "bomb";
    private static final long serialVersionUID = 1113799434508346960L;

    CreatingGameBoard creatingGameBoard;
    int bombRadius;
    long bombExplosionTime = 5;
    private int X;
    private int Y;
    BombThread bombThread;
    Player player;

    public BombCell(Player player, int bombRadius, long bombExplosionTime, CreatingGameBoard creatingGameBoard, int x, int y) {
        super.type = type;
        super.passable = false;
        super.neverPassable = true;
        this.bombRadius = player.bombRadius;
        this.bombExplosionTime = bombExplosionTime;
        this.creatingGameBoard = creatingGameBoard;
        this.player = player;
        this.X = x;
        this.Y = y;
        explode();
    }

    private void explode() {
        bombThread = new BombThread(player, this, creatingGameBoard, X, Y);
        bombThread.start();
    }

    public void explodeNow() throws InterruptedException, IOException {
        bombThread.explode();
    }
}
