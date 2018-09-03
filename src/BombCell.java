import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Sun on 03/31/2018.
 */
public class BombCell extends GameComponent implements Serializable {

    private final String type = "bomb";
    private static final long serialVersionUID = 1113799434508346960L;

    private GameBoardCreator gameBoardCreator;
    private int bombRadius;
    private long bombExplosionTime = 5;
    private int X;
    private int Y;
    private BombThread bombThread;
    private Player player;

    public BombCell(Player player, int bombRadius, long bombExplosionTime, GameBoardCreator gameBoardCreator, int x, int y) {
        super.setType(type);
        super.setPassable(false);
        super.setNeverPassable(true);
        this.bombRadius = player.getBombRadius();
        this.bombExplosionTime = bombExplosionTime;
        this.gameBoardCreator = gameBoardCreator;
        this.player = player;
        this.X = x;
        this.Y = y;
        explode();
    }

    private void explode() {
        bombThread = new BombThread(player, this, gameBoardCreator, X, Y);
        bombThread.start();
    }

    public void explodeNow() throws InterruptedException, IOException {
        bombThread.explode();
    }

    public GameBoardCreator getGameBoardCreator() {
        return gameBoardCreator;
    }

    public void setGameBoardCreator(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public long getBombExplosionTime() {
        return bombExplosionTime;
    }

    public void setBombExplosionTime(long bombExplosionTime) {
        this.bombExplosionTime = bombExplosionTime;
    }

    public BombThread getBombThread() {
        return bombThread;
    }

    public void setBombThread(BombThread bombThread) {
        this.bombThread = bombThread;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void destroy(Player player, int i, int j) {
        BombCell bombCell = this;

        try {
            bombCell.getBombThread().explode();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

