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

    private static final String DACING_QUEEN = SpaceInvaders.class.getResource("ABBA_-_Dancing_Queen.mid").getPath();
    private static final String MOZART = SpaceInvaders.class.getResource("sym40-1.mid").getPath();
    private static final String BEVERLY = SpaceInvaders.class.getResource("Movie_Themes_-_Beverly_Hills_Cop_-_Axel's_Theme_by_Harold_Faltermeyer.mid").getPath();
    private static final String BACKSTREET = SpaceInvaders.class.getResource("Backstreet_Boys_I'll_Never_Break_Your_Heart.mid").getPath();
    private static final String KYOTO = SpaceInvaders.class.getResource("Skrillex_Kyoto_(ft._Sirah).mid").getPath();
    private static final String SPACER = SpaceInvaders.class.getResource("spacer.svg").getPath();
    private static final String BG = SpaceInvaders.class.getResource("background.mov").getPath();
    private static List<String> resourceList = new LinkedList<>();

    static {
        resourceList.addAll(Arrays.asList(DACING_QUEEN, MOZART, BEVERLY, BACKSTREET, KYOTO));
    }

    public static String getRandomTrack() {
        int len = resourceList.size();
        return resourceList.get((int) (Math.random() * len));
    }

    public static String getSPACER() {
        return SPACER;
    }

    public static String getBG() {
        return BG;
    }
}
