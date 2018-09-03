import java.io.IOException;
import java.io.Serializable;


public class BombThread extends Thread implements Serializable {

    private static final long serialVersionUID = 1113799434508346961L;

    private int X;
    private int Y;
    private BombCell bomb;
    private GameBoardCreator gameBoardCreator;
    private Player player;
    private boolean isActive;

    public BombThread(Player player, BombCell bombCell, GameBoardCreator gameBoardCreator, int x, int y) {
        this.bomb = bombCell;
        this.gameBoardCreator = gameBoardCreator;
        this.X = x;
        this.Y = y;
        this.player = player;
        this.isActive = true;
    }

    @Override
    public void run() {
        try {
            if (!player.isBombControl()) {
                Thread.sleep(bomb.getBombExplosionTime() * 1000);

                explode();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void explode() throws InterruptedException, IOException {

        if (isActive) {

            gameBoardCreator.gameComponents[X][Y] = new FieldCell();
            if (player.isBombSet() && player.getCurrentBomb() == bomb) {

                gameBoardCreator.killPlayer(player);

            }

            player.setBombCount(player.getBombCount() - 1);

            player.getBombs().remove(bomb);
            isActive = false;
            for (int i = X - bomb.getBombRadius(); i <= X + bomb.getBombRadius(); i++) {

                destroy(i, Y);
            }

            for (int j = Y - bomb.getBombRadius(); j <= Y + bomb.getBombRadius(); j++) {

                destroy(X, j);


            }
        }

    }


    private void destroy(int i, int j) throws InterruptedException, IOException {
        synchronized (gameBoardCreator.gameComponents) {

            if (i > -1 && j > -1 && i < gameBoardCreator.width + 2 && j < gameBoardCreator.height + 2) {
                if (gameBoardCreator.gameComponents[i][j].isExplosive()) {
                    gameBoardCreator.gameComponents[i][j].destroy(player, i, j);

                }
            }
        }
    }


    public GameBoardCreator getGameBoardCreator() {
        return gameBoardCreator;
    }

    public void setGameBoardCreator(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

