import com.sun.security.ntlm.NTLMException;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main implements Serializable {
    private static ArrayList<ClientThread> sockets = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<>();

    public static void main(String[] args) throws IOException, NTLMException, ClassNotFoundException {



        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            ClientThread clientThread = new ClientThread(socket);
//            ReceiveNewClass ReceiveNewClass =new ReceiveNewClass(clientThread);
//            ReceiveNewClass.start();
            sockets.add(clientThread);

            clientThread.start();

        }
    }


    static Game joinGameRoom(ClientThread client, String gameRoomName) throws IOException {
        for (Game game : games) {
            if (game.roomName.equals(gameRoomName)) {
                game.members.add(client);
                return game;

            }
        }

        client.send("Game room doesn't exist");
        return null;
    }

    static void sendRoomList(ClientThread client) throws IOException {

        for (int i = 0; i < games.size(); i++) {
            client.send(games.get(i).roomName);
            client.getObjectOutputStream().flush();
        }

    }

    public static ArrayList<ClientThread> getSockets() {
        return sockets;
    }

    public static void setSockets(ArrayList<ClientThread> sockets) {
        Main.sockets = sockets;
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void setGames(ArrayList<Game> games) {
        Main.games = games;
    }
}


