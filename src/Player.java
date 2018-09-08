import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.TimerTask;


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
    boolean hasShield = true;
    private boolean usedShield = false;
    private Time shieldTime = new Time(30);



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

    String getName() {
        return name;
    }

    int getPlayerPositionX() {
        return playerPositionX;
    }

    void setPlayerPositionX(int playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    int getPlayerPositionY() {
        return playerPositionY;
    }

    void setPlayerPositionY(int playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }

    boolean isBombSet() {
        return bombSet;
    }

    void setBombSet(boolean bombSet) {
        this.bombSet = bombSet;
    }

    boolean isBombControl() {
        return bombControl;
    }

    void setBombControl(boolean bombControl) {
        this.bombControl = bombControl;
    }

    long getPlayerSleep() {
        return playerSleep;
    }

    void setPlayerSleep(long playerSleep) {
        this.playerSleep = playerSleep;
    }

    ClientThread getClient() {
        return client;
    }


    BombCell getCurrentBomb() {
        return currentBomb;
    }

    ArrayList<BombCell> getBombs() {
        return bombs;
    }

    public void setBombs(ArrayList<BombCell> bombs) {
        this.bombs = bombs;
    }

    @Override
    public void destroy(Player player, int i, int j) {
        try {
            player.killPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void killPlayer() throws IOException {
        if (!hasShield) {

        if (isAlive()) {


                this.getClient().send("#lost$");
            GameBoardCreator.getGameBoardCreator().gameComponents[playerPositionX][playerPositionY] = new FieldCell();


            die();
            }
        }else {
                setShieldTimer();
            }


    }
    private void setShieldTimer() {
        if (!usedShield) {
            usedShield = true;
            shieldTime = new Time(30);
            java.util.Timer timer = new java.util.Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    keepShieldAbility();
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }
    }


    private void keepShieldAbility() {


        if (shieldTime.getTime() > 0) {
            shieldTime.setTime(shieldTime.getTime() - 1);
            // refreshTimer(gameTime.getTime());
        } else {
            hasShield = false;
            usedShield = false;

        }
    }

    public String getType() {
        return type;
    }

    boolean isAlive() {
        return isAlive;
    }

    void setAlive(boolean alive) {
        isAlive = alive;
    }

    int getBombRadius() {
        return bombRadius;
    }

    void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    int getBombNum() {
        return bombNum;
    }

    void setBombNum(int bombNum) {
        this.bombNum = bombNum;
    }

    int getBombCount() {
        return bombCount;
    }

    void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }
    void putPlayerOnShield() {
        hasShield = true;
    }
}