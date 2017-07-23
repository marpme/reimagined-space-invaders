package spacey.interfaces;

import oscP5.OscEventListener;
import spacey.game.SpaceInvaders;
import oscP5.OscMessage;

/**
 * Class description ...
 * Included in PACKAGE_NAME
 *
 * @author Marvin Piekarek (s0556014)
 * @version 1.0
 * @since 03. Jul 2017
 */
public class OSCListener implements OscEventListener {

    private SpaceInvaders si;

    public OSCListener(SpaceInvaders e) {
        this.si = e;
    }

    @Override
    public void oscEvent(OscMessage theOscMessage) {
        if(theOscMessage.checkAddress("/1/push2")
                || theOscMessage.checkAddress("/1/push1")) {
            si.velocityX = 0;
            return;
        }

        if(theOscMessage.checkAddress("/1/push2")) {
            si.velocityX = 2 * (float)Math.log(si.speed);
        } else if(theOscMessage.checkAddress("/1/push1")) {
            si.velocityX = -1 * (2 * (float)Math.log(si.speed));
        }
    }
}
