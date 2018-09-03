import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sun on 08/10/2018.
 */
public class Game implements Serializable {
    ArrayList<ClientThread> members = new ArrayList<>();
    String name;
    String roomName;
    int width;
    int height;
    ArrayList<Player> players = new ArrayList<>();
    GameBoardCreator gameBoardCreator;
    int level = 1;


    public Game(String roomName, int width, int height) throws IOException {
        this.roomName = roomName;
        this.width = width;
        this.height = height;


    }

    public void addPlayer(Player player) throws IOException {
        gameBoardCreator.addPlayer(player);

    }

    public void start() throws IOException {
        gameBoardCreator = new GameBoardCreator(this, level, width, height);
        gameBoardCreator.init();
        gameBoardCreator.players = players;
    }

    public void gotoNextLevel() throws IOException {
        gameBoardCreator.level = gameBoardCreator.level + 1;
        gameBoardCreator.gameComponents = null;
        gameBoardCreator.init();
        // gameBoardCreator = new GameBoardCreator(this, level, width, height);
        gameBoardCreator.gameBoardCreator = gameBoardCreator;
//        gameBoardCreator.init();
        for (int i = 0; i < members.size(); i++) {
            members.get(i).getPlayer().setAlive(true);
            members.get(i).sendTime();


        }

        gameBoardCreator.players = players;
    }
//
//    public void sendTime(Time gameTime) throws IOException {
//        for (int i = 0; i <members.size() ; i++) {
//            members.get(i).send("#time$"+String.valueOf(gameTime.getTime()));
//            System.out.println(gameTime.getTime());
//
//        }
//    }
}
