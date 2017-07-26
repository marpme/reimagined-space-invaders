package spacey.game;

import java.net.URL;
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

    private static final URL DACING_QUEEN = ResourcesHandler.class.getResource("ABBA_-_Dancing_Queen.mid");
    private static final URL MOZART = ResourcesHandler.class.getResource("sym40-1.mid");
    private static final URL BEVERLY = ResourcesHandler.class.getResource("Movie_Themes_-_Beverly_Hills_Cop_-_Axel's_Theme_by_Harold_Faltermeyer.mid");
    private static final URL BACKSTREET = ResourcesHandler.class.getResource("Backstreet_Boys_I'll_Never_Break_Your_Heart.mid");
    private static final URL KYOTO = ResourcesHandler.class.getResource("Skrillex_Kyoto_(ft._Sirah).mid");

    public static final String SPACER = ResourcesHandler.class.getResource("spacer.svg").getPath();
    public static final String BACKGROUND = ResourcesHandler.class.getResource("bg.jpg").getPath();
    public static final String NOTE = ResourcesHandler.class.getResource("musical-note.svg").getPath();

    public static List<URL> resourceList = new LinkedList<>();

    static {
        resourceList.addAll(Arrays.asList(DACING_QUEEN, MOZART, BEVERLY, BACKSTREET, KYOTO));
    }

    public static URL getRandomTrack() {
        int len = resourceList.size();
        return resourceList.get((int) (Math.random() * len));
    }

}
