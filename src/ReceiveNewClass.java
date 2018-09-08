import com.sun.org.apache.bcel.internal.util.ClassLoader;

import java.io.*;

/**
 * Created by Sun on 09/01/2018.
 */
public class ReceiveNewClass extends Thread {

    String type;
    private Class cls = null;
    private ClientThread client;
    GameBoardCreator gameBoardCreator;


    ReceiveNewClass( ClientThread client) throws IOException, ClassNotFoundException {
        this.client = client;
        this.gameBoardCreator = gameBoardCreator;
        loadNewClass();
    }

    private void loadNewClass() throws IOException, ClassNotFoundException {
        File file = new File("C:/Users/Sun/IdeaProjects/BombermanGameServer/update");
        File[] files = file.listFiles();
        if (files.length > 0) {
            String finalFileName = files[0].getName();
//            FileInputStream fileInputStream=new FileInputStream(files[0]);
//             objectInputStream=new ObjectInputStream(fileInputStream);
//            Class clss=(Class) objectInputStream.readObject();
           // System.out.println(clss.getName());
            //Class clss=(Class) (files[0]);

            ClassLoader classLoader = new ClassLoader();

            try {
                cls = classLoader.loadClass(finalFileName);
                System.out.println(cls.getName());
                client.sendObject(cls, false);
                attachToProgram();


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void attachToProgram() {
        try {
            Enemy enemy = (Enemy) cls.newInstance();
            if (enemy.getLevel() > gameBoardCreator.getMaximumEnemyLevel()) {
                gameBoardCreator.setMaximumEnemyLevel(enemy.getLevel());
            }
            gameBoardCreator.getGame().placeEnemyInLevelArray(enemy);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    private void extractFields() {
//        try {
//            sleep = cls.getClass().getDeclaredField("sleep").getInt(cls.newInstance());
//            level = cls.getClass().getDeclaredField("level").getInt(cls.newInstance());
//            type = (String) cls.getClass().getDeclaredField("type").get(cls.newInstance());
//            isGhosting = cls.getClass().getDeclaredField("isGhosting").getBoolean(cls.newInstance());
//            image = (ImageIcon) cls.getClass().getDeclaredField("image").get(cls.newInstance());
//
//
//        } catch (NoSuchFieldException e) {
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//
//    }

}

