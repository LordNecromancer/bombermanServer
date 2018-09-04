import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.time.Instant;
import java.util.*;
import java.util.Timer;

public class GameBoardCreator extends JFrame implements Serializable {

    int width;
    int height;
    int enemyCount;
    private static final long serialVersionUID = 1113799434508676095L;

    GameComponent[][] gameComponents;
    private ArrayList<ObstacleCell> obstacleArray = new ArrayList<>();
    Time gameTime;
    private int obstacle = Math.min(width, height);
    static final int obstacleScore = 10;
    private static long bombExplosionTime = 5;
    EnemyMovementThread enemyMove = new EnemyMovementThread(this);
    EnemyMovementThreadTypeTwo enemyMove2 = new EnemyMovementThreadTypeTwo(this);

    private int bombCount = 0;
    private boolean isMoving = false;
    private boolean threadActive;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    static GameBoardCreator gameBoardCreator;
    private Date date;
    private Game game;
    int level;

    public GameBoardCreator(Game game, int level, int width, int height) {

        this.game = game;
        this.width = width;
        this.height = height;
        this.level = level;
        this.level = 4;
        this.bombCount = bombCount;
        this.gameTime = new Time(300);
        isMoving = false;
        threadActive = false;
        date = Date.from(Instant.now());
        this.gameBoardCreator = this;
        obstacle = Math.min(width, height);
    }

    public static GameBoardCreator getGameBoardCreator() {
        return gameBoardCreator;
    }

    public long getBombExplosionTime() {
        return bombExplosionTime;
    }


    public void init() throws IOException {

        if (gameComponents == null) {
            gameComponents = new GameComponent[width + 2][height + 2];
            createNewBoard(width, height);
        } else {
            //  loadExistingBoard();
        }
    }


    private void createNewBoard(int w, int h) throws IOException {

        for (int i = 0; i < w + 2; i++) {

            for (int j = 0; j < h + 2; j++) {

                GameComponent gameComponent;


                if (i == 0 || j == 0 || i == w + 1 || j == h + 1) {


                    WallCell wallCell = new WallCell();
                    gameComponent = wallCell;
                    wallCell.setNeverPassable(true);
                } else if (i % 2 == 0 && j % 2 == 0) {
                    gameComponent = new WallCell();
                } else {
                    gameComponent = new FieldCell();
                }

                this.gameComponents[i][j] = gameComponent;
            }
        }

        //setUpLabels();
        dispersePlayers();
        createRandomEnemies(width, height);
        createRandomObstacleCell(width, height);
    }

    private void dispersePlayers() throws IOException {
        for (int i = 0; i < game.players.size(); i++) {
            putPlayerInTheField(game.players.get(i));

        }
    }

    private void createRandomEnemies(int width, int height) {

        int enemyNum = Math.min(width, height) / 2;
        // int enemyNum = 0;
        enemyCount = enemyNum;
        for (int i = 0; i < enemyNum; ) {
            Random r = new Random();
            int n = r.nextInt(width - 1) + 1;
            int m = r.nextInt(height - 1) + 1;
            if (gameComponents[n][m].getType() == "field") {

                if (n != 2 && m != 2) {
                    i++;
                    Enemy enemy = createRandomEnemy();
                    this.gameComponents[n][m] = enemy;
                    enemies.add(enemy);

                }
            }
        }
    }

    private Enemy createRandomEnemy() {
        int imaginaryLevel = level;
        if (level > 4) {
            imaginaryLevel = 4;
        }
        int randomNum = new Random().nextInt(imaginaryLevel) + 1;


        if (randomNum == 1) {
            return new EnemyLvL1();
        } else if (randomNum == 2) {
            return new EnemyLvL2();
        } else if (randomNum == 3) {
            return new EnemyLvL3();
        } else {
            return new EnemyLvL4();
        }
    }

    private void createRandomObstacleCell(int w, int h) throws IOException {

        for (int k = 0; k < obstacle; ) {

            Random r = new Random();
            int n = r.nextInt(w - 1) + 1;
            int m = r.nextInt(h - 1) + 1;
            if (gameComponents[n][m].getType().equals("field")) {

                if (n != 2 && m != 2) {
                    k++;
                    ObstacleCell obstacleCell = new ObstacleCell();
                    this.gameComponents[n][m] = obstacleCell;
                    obstacleArray.add(obstacleCell);

                }
            }
        }
        int randomNum = getRandomNumber(obstacleArray.size());
        ObstacleCell obstacleCell = obstacleArray.get(randomNum);
        obstacleCell.hasDoor = true;
        setUpStatChangers();
    }

    private PowerUps getRandomPowerUp() {
        ArrayList<PowerUps> powerUps = new ArrayList<>();
        powerUps.add(new IncreasingBombsPowerUp());
        powerUps.add(new IncreasingPointsPowerUp(this));
        powerUps.add(new IncreasingRadiusPowerUp());
        powerUps.add(new IncreasingSpeedPowerUp(this));
        powerUps.add(new BombControlPowerUp(this));

        Random r = new Random();
        int m = r.nextInt(powerUps.size() - 1);
        return powerUps.get(m);
    }

    private Poison getRandomPoison() {
        ArrayList<Poison> poisons = new ArrayList<>();
        poisons.add(new DecreasingBombsPoison());
        poisons.add(new DecreasingPointsPoison(this));
        poisons.add(new DecreasingRadiusPoison());
        poisons.add(new DecreasingSpeedPoison(this));
        poisons.add(new LosingBombControlPoison());

        Random r = new Random();
        int m = r.nextInt(poisons.size() - 1);
        return poisons.get(m);
    }

    private void setUpStatChangers() throws IOException {
        for (int k = 0; k <= obstacle / 3; ) {

            Random r = new Random();
            int n = r.nextInt(obstacleArray.size() - 1);
            int m = r.nextInt(2);
            StatChanger statChanger;
            ObstacleCell obstacleCell = obstacleArray.get(n);
            if (m == 1) {
                statChanger = getRandomPowerUp();
                obstacleCell.powerUps = (PowerUps) statChanger;
            }
            if (m == 0) {
                statChanger = getRandomPoison();
                obstacleCell.poison = (Poison) statChanger;
            }
            k++;
        }
        if (!threadActive) {
            threadActive = true;
            enemyMove.start();
            enemyMove2.start();
            setSendingTimer();
            setTimer();
        }
    }

    private void setSendingTimer() {
        Timer time = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    sendArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        time.scheduleAtFixedRate(timerTask, 0, 250);
    }

    public void sendArray() throws IOException {


        if (gameComponents != null) {
            synchronized (gameComponents) {
                for (int i = game.members.size() - 1; i >= 0; i--) {
                    game.members.get(i).sendObject(gameComponents, false);
                }
            }
        }
    }


    private int getRandomNumber(int bound) {

        Random random = new Random();

        if (bound == 0) {
            return -1;
        }
        if (bound == 1) {
            return 0;
        } else {
            int randomNum = random.nextInt(bound - 1);
            return randomNum;
        }
    }

    synchronized void setGameComponents(int i, int j, GameComponent gameComponent) {
        synchronized (gameComponents) {
            gameComponents[i][j] = gameComponent;
        }
    }


    void killPlayer(Player player) throws IOException {
        if (player.isAlive()) {

            player.getClient().send("#lost$");
        }
        player.die();
    }


    boolean isPassed(Player player) {
        return Date.from(Instant.now()).getTime() - date.getTime() > player.getPlayerSleep() * 100;
    }

    void checkIfIsStatChanger(Player player, GameComponent cell) throws IOException {

        StatChanger statChanger = (StatChanger) cell;
        statChanger.setPassable(true);
        statChanger.doYourThing(player);
    }


    void addScore(Player player, int score) throws IOException {

        if (player.getScore() + score > 0) {
            player.setScore(player.getScore() + score);
        } else {
            player.setScore(0);
        }
        sendScore();
    }

    private void setTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    calculateTime();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void calculateTime() throws IOException {


        if (gameTime.getTime() > 0) {
            gameTime.setTime(gameTime.getTime() - 1);
            sendTime();
        } else {
            for (int i = 0; i < game.players.size(); i++) {
                game.players.get(i).addScore(-1);

            }
        }
    }

    private void sendTime() throws IOException {
        //game.sendTime(gameTime);
    }

    private void sendScore() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#score$");

        for (int i = 0; i < game.players.size(); i++) {

            stringBuilder.append(game.players.get(i).getName() + "    :    " + game.players.get(i).getScore() + "       ");
            //  stringBuilder.append(System.getProperty("line.separator"));


        }
        for (int i = 0; i < game.players.size(); i++) {

            game.players.get(i).getClient().send((stringBuilder.toString()));
            game.players.get(i).getClient().getObjectOutputStream().reset();

        }
        System.out.println(stringBuilder.toString());

    }

    boolean checkIfPassable() {
        if (enemyCount == 0) {
            return true;
        }
        return false;
    }

    void goToNextLevel() throws IOException {

        enemies = new ArrayList<>();
        obstacleArray = new ArrayList<>();
        enemyMove = new EnemyMovementThread(this);
        enemyMove2 = new EnemyMovementThreadTypeTwo(this);
        threadActive = false;
        enemyCount = 0;
        gameTime = new Time(300);
        game.gotoNextLevel();
    }

    public void addPlayer(Player player) throws IOException {
        // players.add(player);
        putPlayerInTheField(player);
    }

    private void putPlayerInTheField(Player player) throws IOException {
        while (true) {
            Random r = new Random();
            int n = r.nextInt(width - 1) + 1;
            int m = r.nextInt(height - 1) + 1;
            if (gameComponents[n][m].getType().equals("field")) {
                synchronized (gameComponents) {
                    gameComponents[n][m] = player;
                }
                player.setPlayerPositionX(n);
                player.setPlayerPositionY(m);
                player.getClient().sendPlayerLocation();
                break;

            }
        }
    }


    void setPlayerPosition(Player player, int playerX, int playerY) throws IOException {
        GameComponent cell = gameBoardCreator.gameComponents[playerX][playerY];

        synchronized (gameComponents) {

            if (player.isAlive()) {
                if (cell instanceof StatChanger) {
                    gameBoardCreator.checkIfIsStatChanger(player, cell);
                }

                if (cell.getPassable()) {

                    gameComponents[playerX][playerY] = player;
                    if (player.isBombSet()) {
                        gameComponents[player.getPlayerPositionX()][player.getPlayerPositionY()] = player.getCurrentBomb();
                    } else {
                        gameComponents[player.getPlayerPositionX()][player.getPlayerPositionY()] = new FieldCell();
                    }
                    player.setBombSet(false);

                    player.setPlayerPositionX(playerX);
                    player.setPlayerPositionY(playerY);
                }

                if (cell.getType().equals("door")) {
                    if (gameBoardCreator.checkIfPassable()) {
                        addScore(player, 100);
                        gameBoardCreator.goToNextLevel();
                    }
                }
                player.getClient().sendPlayerLocation();

            }
        }
    }

    public void explodeNow(Player player) {
        if (player.isBombControl() && player.isAlive()) {
            try {
                if (player.getBombs().size() > 0) {
                    player.getBombs().get(0).getBombThread().explode();
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void updatePlayerPosition(Player player) throws IOException {
        synchronized (gameComponents) {
            for (int i = 0; i < game.players.size(); i++) {
                gameComponents[game.players.get(i).getPlayerPositionX()][game.players.get(i).getPlayerPositionY()] = new FieldCell();

            }
            game.players.remove(player);
            for (int i = 0; i < game.players.size(); i++) {
                gameComponents[game.players.get(i).getPlayerPositionX()][game.players.get(i).getPlayerPositionY()] = game.players.get(i);
                game.players.get(i).getClient().sendPlayerLocation();
            }

        }

    }
}
