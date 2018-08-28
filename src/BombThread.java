import java.io.IOException;
import java.io.Serializable;


public class BombThread extends Thread implements Serializable {

    private static final long serialVersionUID = 1113799434508346961L;

    private int X;
    private int Y;
    private BombCell bomb;
    CreateBoard createBoard;
    Player player;
    private boolean isActive;

    public BombThread(Player player, BombCell bombCell, CreateBoard createBoard, int x, int y) {
        this.bomb = bombCell;
        this.createBoard = createBoard;
        this.X = x;
        this.Y = y;
        this.player = player;
        this.isActive = true;
    }

    @Override
    public void run() {
        try {
            if (!player.bombControl) {
                Thread.sleep(bomb.bombExplosionTime * 1000);

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

            player.bombCount--;

            player.bombs.remove(bomb);
            isActive = false;
            for (int i = X - bomb.bombRadius; i <= X + bomb.bombRadius; i++) {

                destroy(i, Y);
            }

            for (int j = Y - bomb.bombRadius; j <= Y + bomb.bombRadius; j++) {

                destroy(X, j);


            }
        }

    }


    private void destroy(int i, int j) throws InterruptedException, IOException {
        synchronized (createBoard.gameComponents) {

            if (i > -1 && j > -1 && i < createBoard.width + 2 && j < createBoard.height + 2) {
                if (createBoard.gameComponents[i][j].isExplosive) {
                    if (createBoard.gameComponents[i][j].type.equals("obstacle")) {
                        destroyObstacle(createBoard.gameComponents[i][j], i, j);

                    } else {
                        if (player.bombSet && player.currentBomb == bomb) {
                            createBoard.killPlayer(player);
                        } else if (createBoard.gameComponents[i][j].type.equals("player")) {
                            createBoard.killPlayer((Player) createBoard.gameComponents[i][j]);
                        } else if (createBoard.gameComponents[i][j].type.equals("bomb") && (i != X || j != Y)) {

                            destroyOtherBombs(createBoard.gameComponents[i][j]);
                        } else if (createBoard.gameComponents[i][j] instanceof Enemy) {

                            destroyEnemy(createBoard.gameComponents[i][j]);
                        }

                        createBoard.gameComponents[i][j] = new FieldCell();
                    }
                }
            }
        }
    }


    private void destroyEnemy(GameComponent gameComponent) throws IOException {
        Enemy enemy = (Enemy) gameComponent;
        createBoard.enemyCount--;
        createBoard.addScore(player, 20 * enemy.level);
    }

    private void destroyOtherBombs(GameComponent gameComponent) throws InterruptedException, IOException {
        BombCell bombCell = (BombCell) gameComponent;
        if (player.bombSet && player.currentBomb == bomb) {
            createBoard.killPlayer(player);
        }
        bombCell.bombThread.explode();
    }

    private void destroyObstacle(GameComponent gameComponent, int i, int j) throws IOException {
        synchronized (createBoard.gameComponents) {
            ObstacleCell obstacleCell = (ObstacleCell) gameComponent;
            createBoard.addScore(player, CreateBoard.obstacleScore);
            if (obstacleCell.hasDoor) {
                System.out.println(1);
                createBoard.gameComponents[i][j] = new Door();


            } else if (obstacleCell.powerUps != null) {
                System.out.println("2");
                createBoard.gameComponents[i][j] = obstacleCell.powerUps;


            } else if (obstacleCell.poison != null) {
                System.out.println(3);
                createBoard.gameComponents[i][j] = obstacleCell.poison;

            } else {
                System.out.println(4);
                createBoard.gameComponents[i][j] = new FieldCell();
            }
        }
    }
}
