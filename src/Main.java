import com.sun.security.ntlm.NTLMException;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main implements Serializable {
    static ArrayList<ClientThread> sockets = new ArrayList<>();
    static ArrayList<Game> games = new ArrayList<>();

    public static void main(String[] args) throws IOException, NTLMException, ClassNotFoundException {


        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            ClientThread clientThread = new ClientThread(socket);
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
            client.objectOutputStream.flush();
        }

    }
}


