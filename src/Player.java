import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class Player extends GameComponent implements Serializable {

    private String name;
    private int playerPositionX;
    private int playerPositionY;
    private int score;
    private static final long serialVersionUID = 1114799434508306969L;

    private boolean bombSet = false;
    private boolean bombControl = false;
    private long playerSleep = 4;
    private ClientThread client;
    private BombCell currentBomb = null;
    private ArrayList<BombCell> bombs = new ArrayList<>();
    private final String type = "player";
    private boolean isAlive;
    private int bombRadius = 1;
    private int bombNum = 1;
    private int bombCount = 1;


    public Player(String name, ClientThread client) {
        this.client = client;
        super.setType(type);
        super.setPassable(true);
        this.name = name;
        this.isAlive = true;
    }

    void die() {
        this.isAlive = false;
        this.bombSet = false;
    }

    void addScore(int score) throws IOException {

        if (this.score + score > 0) {
            this.score += score;
        } else {
            this.score = 0;
        }

    }

    void plantBomb(GameBoardCreator gameBoardCreator) {
        if (this.bombCount <= this.bombNum && !this.bombSet) {
            this.bombCount++;
            this.bombSet = true;
            BombCell bomb = new BombCell(this, this.bombRadius, gameBoardCreator.getBombExplosionTime(), gameBoardCreator, playerPositionX, playerPositionY);
            this.bombs.add(bomb);
            synchronized (gameBoardCreator.gameComponents) {
                gameBoardCreator.gameComponents[playerPositionX][playerPositionY] = bomb;
            }
            this.currentBomb = bomb;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public void setPlayerPositionX(int playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    public int getPlayerPositionY() {
        return playerPositionY;
    }

    public void setPlayerPositionY(int playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isBombSet() {
        return bombSet;
    }

    public void setBombSet(boolean bombSet) {
        this.bombSet = bombSet;
    }

    public boolean isBombControl() {
        return bombControl;
    }

    public void setBombControl(boolean bombControl) {
        this.bombControl = bombControl;
    }

    public long getPlayerSleep() {
        return playerSleep;
    }

    public void setPlayerSleep(long playerSleep) {
        this.playerSleep = playerSleep;
    }

    public ClientThread getClient() {
        return client;
    }

    public void setClient(ClientThread client) {
        this.client = client;
    }

    public BombCell getCurrentBomb() {
        return currentBomb;
    }

    public void setCurrentBomb(BombCell currentBomb) {
        this.currentBomb = currentBomb;
    }

    public ArrayList<BombCell> getBombs() {
        return bombs;
    }

    public void setBombs(ArrayList<BombCell> bombs) {
        this.bombs = bombs;
    }

    @Override
    public void destroy(Player player, int i, int j) {
        player.die();
        GameBoardCreator.getGameBoardCreator().gameComponents[i][j] = new FieldCell();
    }

    public String getType() {
        return type;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public int getBombNum() {
        return bombNum;
    }

    public void setBombNum(int bombNum) {
        this.bombNum = bombNum;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }
}