import java.io.Serializable;

/**
 * Created by Sun on 09/03/2018.
 */

    import java.io.Serializable;

    /**
     * Created by Sun on 06/06/2018.
     */
    public class EnemyLvL5 extends Enemy implements Serializable {
        final private String type = "enemyLvL3";
        private int level = 3;
        static int sleep = 4;

        public EnemyLvL5() {
            super.setLevel(level);
            super.setType(type);
            super.setSleep(sleep);
            super.setPassable(false);
            super.setNeverPassable(true);


        }
    }


