import java.io.Serializable;
import java.util.ArrayList;


public class Player extends GameComponent implements Serializable {

    String name;
    int playerPositionX;
    int playerPositionY;
    int score;
    private static final long serialVersionUID = 1114799434508306969L;

    boolean bombSet = false;
    boolean bombControl = false;
    long playerSpeed = 4;
    ClientThread client;
    public BombCell currentBomb = null;
    ArrayList<BombCell> bombs = new ArrayList<>();
    final String type = "player";
    boolean isAlive;
    int bombRadius = 1;
    int bombNum = 1;
    int bombCount = 1;


    public Player(String name, ClientThread client) {
        this.client = client;
        super.type = type;
        super.passable = true;
        this.name = name;
        this.isAlive = true;
    }

    public void die() {
        this.isAlive = false;
        this.bombSet = false;
    }

    public void addScore(int i) {
        score += i;
    }
}