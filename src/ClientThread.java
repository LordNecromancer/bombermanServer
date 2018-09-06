import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread implements Serializable {
    transient private Socket socket;
    private transient ObjectOutputStream objectOutputStream;
    private transient ObjectInputStream objectInputStream;
    private boolean justJoinedChatRoom = false;
    private static ClientThread client;
    private String name = "Anonymous";
    private String roomName = null;
    private Game gameRoom;
    private int width;
    private int height;
    private Player player;
    private int playerX;
    private int playerY;
    private boolean isActive = true;


    public ClientThread(Socket socket) throws IOException {
        client = this;
        this.socket = socket;
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

    }

    public static ClientThread getClient() {
        return client;
    }

    public static void setClient(ClientThread client) {
        ClientThread.client = client;
    }


    @Override
    public void run() {


        String line;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {

            try {

                Object object = objectInputStream.readObject();


                if (object instanceof String) {
                    line = (String) object;

                    System.out.println(line);

                    //  line = dataInputStream.readUTF();
                    // System.out.println(line);
                    if (line.startsWith("#player$")) {

                    }


                    if (line.startsWith("@")) {
                        joinRoom(line);
                    } else if (line.startsWith("#name$")) {
                        name = line.substring(6);
                    } else if (line.startsWith("#width$")) {
                        width = Integer.valueOf(line.substring(7));
                    } else if (line.startsWith("#height$")) {
                        height = Integer.valueOf(line.substring(8));

                    } else if (line.startsWith("#roomName$")) {
                        this.roomName = line.substring(10);
                        createGame();
                    } else if (line.equals("#getRoomsList$")) {
                        Main.sendRoomList(this);
                    } else if (line.equals("#bomb$")) {
                        player.plantBomb(GameBoardCreator.getGameBoardCreator());
                    } else if (line.startsWith("#playerX$")) {
                        playerX = Integer.valueOf(line.substring(9));
                        //   GameBoardCreator.gameBoardCreator.player.playerPositionX=Integer.valueOf(line.substring(8));
                    } else if (line.equals("#now$")) {
                        GameBoardCreator.getGameBoardCreator().explodeNow(player);

                    } else if (line.startsWith("#playerY$")) {

                        playerY = Integer.valueOf(line.substring(9));
                        GameBoardCreator.getGameBoardCreator().setPlayerPosition(player, playerX, playerY);
                        //  GameBoardCreator.gameBoardCreator.player.playerPositionY=Integer.valueOf(line.substring(8));
                    }
//                        } else {
//                            handlingUserMassages(line);
//                        }
                }


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
//                try {
//                 //   closeConnection();
//                  //  break;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }


        }
    }

    private void handlingUserMassages(String line) throws IOException {
        if (gameRoom != null) {
            if (!justJoinedChatRoom) {
                justJoinedChatRoom = true;
                send("JoinedGameRoom$");

            } else {


                //   Main.sendToAll(this, name + "  :  " + line);
            }
        } else {


            send("NoSuchRoom$");
        }
    }

    private void joinRoom(String line) throws IOException {
        gameRoom = Main.joinGameRoom(this, line);
        if (gameRoom != null) {
            send("JoinedGameRoom$");
            player = new Player(name, this);
            gameRoom.gameBoardCreator.addPlayer(player);
            gameRoom.players.add(player);
            sendTime();
            justJoinedChatRoom = true;
            send("#width$" + gameRoom.width);
            send("#height$" + gameRoom.height);
            send("#roomName$" + gameRoom.roomName);
        }
    }

    void sendTime() throws IOException {
        send("#time$" + gameRoom.gameBoardCreator.gameTime.getTime());
    }

    private void createGame() throws IOException {

        Game newGame = new Game("@" + roomName, width, height);
        player = new Player(name, this);
        gameRoom = newGame;
        gameRoom.players.add(player);
        Main.getGames().add(newGame);
        gameRoom.members.add(this);
        send("JoinedGameRoom$");
        gameRoom.start();
        sendTime();
        System.out.println(gameRoom.width);

        send("#width$" + gameRoom.width);
        send("#height$" + gameRoom.height);
        send("#roomName$" + gameRoom.roomName);

        justJoinedChatRoom = true;
    }


    synchronized void sendObject(Object object, boolean isMsg) throws IOException {


        try {

            if (isActive) {
                objectOutputStream.writeObject(object);
                objectOutputStream.reset();
            }

        } catch (SocketException e) {

            isActive = false;
            gameRoom.members.remove(this);
            gameRoom.gameBoardCreator.updatePlayerPosition(player);
            socket.close();
            Main.getSockets().remove(this);
        }
    }


    void send(String msg) throws IOException {
        sendObject(msg, true);
    }

    void sendPlayerLocation() throws IOException {
        if(player.isAlive()) {
            send("#playerX$" + player.getPlayerPositionX());
            send("#playerY$" + player.getPlayerPositionY());
        }
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

