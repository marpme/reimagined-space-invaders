package spacey.game;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class description ...
 * Included in spacey.game
 *
 * @author Marvin Piekarek (s0556014)
 * @version 1.0
 * @since 19. Jul 2017
 */
public class ResourcesHandler {

    private static final String DACING_QUEEN = ResourcesHandler.class.getResource("ABBA_-_Dancing_Queen.mid").getPath();
    private static final String MOZART = ResourcesHandler.class.getResource("sym40-1.mid").getPath();
    private static final String BEVERLY = ResourcesHandler.class.getResource("Movie_Themes_-_Beverly_Hills_Cop_-_Axel's_Theme_by_Harold_Faltermeyer.mid").getPath();
    private static final String BACKSTREET = ResourcesHandler.class.getResource("Backstreet_Boys_I'll_Never_Break_Your_Heart.mid").getPath();
    private static final String KYOTO = ResourcesHandler.class.getResource("Skrillex_Kyoto_(ft._Sirah).mid").getPath();

    public static final String SPACER = ResourcesHandler.class.getResource("spacer.svg").getPath();
    public static final String BACKGROUND = ResourcesHandler.class.getResource("bg.jpg").getPath();
    public static final String NOTE = ResourcesHandler.class.getResource("musical-note.svg").getPath();

    public static List<String> resourceList = new LinkedList<>();

    static {
        resourceList.addAll(Arrays.asList(DACING_QUEEN, MOZART, BEVERLY, BACKSTREET, KYOTO));
    }

    public static String getRandomTrack() {
        int len = resourceList.size();
        return resourceList.get((int) (Math.random() * len));
    }

}
