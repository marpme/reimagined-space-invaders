package spacey.interfaces;

import oscP5.*;
import spacey.game.SpaceInvaders;

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

    public void oscEvent(OscMessage message) {
        if ((message.checkAddrPattern("/1/push2") && si.velocityX != 0)
                || (message.checkAddrPattern("/1/push1") && si.velocityX != 0)) {
            si.velocityX = 0;
            return;
        }

        if (message.checkAddrPattern("/1/push2")) {
            si.velocityX = 5 * (float) Math.log(si.speed);
        } else if (message.checkAddrPattern("/1/push1")) {
            si.velocityX = -1 * (5 * (float) Math.log(si.speed));
        }
    }

    ;

    public void oscStatus(OscStatus status) {
        System.out.println("status received: " + status);
    }

    ;


}
