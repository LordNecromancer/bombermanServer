import com.sun.org.apache.bcel.internal.util.ClassLoader;

import java.io.File;

/**
 * Created by Sun on 09/01/2018.
 */
public class ReceiveNewClass extends Thread {

    ReceiveNewClass() {
        ClassLoader classLoader = new ClassLoader();
        loadNewClass();
    }

    private void loadNewClass() {
        File file = new File("C:/Users/Sun/IdeaProjects/BombermanGameServer/update");
        File[] files = file.listFiles();
        if (files.length > 0) {
            String finalFileName = files[0].getName();

            ClassLoader classLoader = new ClassLoader();
            Class cls = null;
            try {
                cls = classLoader.loadClass(finalFileName).getClass();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(cls.getName());

        }
    }

    private void createUserInterface() throws ClassNotFoundException {

    }
}

