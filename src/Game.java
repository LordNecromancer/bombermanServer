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
    CreatingGameBoard creatingGameBoard;
    int level = 1;


    public Game(String roomName, int width, int height) throws IOException {
        this.roomName = roomName;
        this.width = width;
        this.height = height;


    }

    public void addPlayer(Player player) throws IOException {
        creatingGameBoard.addPlayer(player);

    }

    public void start() throws IOException {
        creatingGameBoard = new CreatingGameBoard(this, level, width, height);
        creatingGameBoard.init();
        creatingGameBoard.players = players;
    }

    public void gotoNextLevel() throws IOException {
        creatingGameBoard.level = creatingGameBoard.level + 1;
        creatingGameBoard.gameComponents = null;
        creatingGameBoard.init();
        // creatingGameBoard = new CreatingGameBoard(this, level, width, height);
        CreatingGameBoard.creatingGameBoard = creatingGameBoard;
//        creatingGameBoard.init();
        for (int i = 0; i < members.size(); i++) {
            members.get(i).player.isAlive = true;
            members.get(i).sendTime();


        }

        creatingGameBoard.players = players;
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
