import com.sun.org.apache.bcel.internal.util.ClassLoader;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sun on 09/01/2018.
 */
public class ReceiveNewClass extends Thread {
    int sleep;
    int level;
    String type;
    Class cls = null;
    boolean isGhosting;
    ClientThread client;
    ImageIcon image;
    GameBoardCreator gameBoardCreator;


    ReceiveNewClass(GameBoardCreator gameBoardCreator, ClientThread client) {
        this.client = client;
        this.gameBoardCreator = gameBoardCreator;
        loadNewClass();
    }

    private void loadNewClass() {
        File file = new File("C:/Users/Sun/IdeaProjects/BombermanGameServer/update");
        File[] files = file.listFiles();
        if (files.length > 0) {
            String finalFileName = files[0].getName();
            //Class clss=(Class) (files[0]);

            ClassLoader classLoader = new ClassLoader();

            try {
                cls = classLoader.loadClass(finalFileName);
                client.sendObject(cls, false);


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void extractFields() {
        try {
            sleep = cls.getClass().getDeclaredField("sleep").getInt(cls.newInstance());
            level = cls.getClass().getDeclaredField("level").getInt(cls.newInstance());
            type = (String) cls.getClass().getDeclaredField("type").get(cls.newInstance());
            isGhosting = cls.getClass().getDeclaredField("isGhosting").getBoolean(cls.newInstance());
            image = (ImageIcon) cls.getClass().getDeclaredField("image").get(cls.newInstance());


        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void createUserInterface() throws ClassNotFoundException {

    }
}

